const readline = require('readline');
const ContactManager = require('../services/ContactManager');

// Colores ANSI simples
const colors = {
  reset: '\x1b[0m',
  red: '\x1b[31m',
  green: '\x1b[32m',
  yellow: '\x1b[33m',
  blue: '\x1b[34m',
  cyan: '\x1b[36m',
  magenta: '\x1b[35m',
  white: '\x1b[37m',
  bgBlue: '\x1b[44m',
  bold: '\x1b[1m'
};

class CLI {
  constructor() {
    this.manager = new ContactManager();
    this.rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout
    });
  }

  // Función helper para colorear texto
  color(text, colorCode) {
    return `${colorCode}${text}${colors.reset}`;
  }

  async start() {
    console.log(this.color('\n  📱 GESTOR DE CONTACTOS v1.0  ', colors.bgBlue + colors.white));
    console.log(this.color('====================================\n', colors.blue));
    
    await this.manager.init();
    this.showMenu();
  }

  showMenu() {
    console.log(this.color('\nMenú Principal:', colors.cyan));
    console.log('1. ➕ Agregar contacto');
    console.log('2. 🔍 Buscar contacto');
    console.log('3. 👁️  Ver todos los contactos');
    console.log('4. ✏️  Editar contacto');
    console.log('5. 🗑️  Eliminar contacto');
    console.log('6. ❌ Salir');
    
    this.rl.question(this.color('\nSeleccione una opción (1-6): ', colors.yellow), async (option) => {
      switch (option.trim()) {
        case '1': await this.addContact(); break;
        case '2': await this.searchContact(); break;
        case '3': await this.listContacts(); break;
        case '4': await this.editContact(); break;
        case '5': await this.deleteContact(); break;
        case '6': 
          console.log(this.color('\n👋 ¡Hasta pronto!\n', colors.green));
          this.rl.close();
          return;
        default:
          console.log(this.color('⚠️ Opción inválida', colors.red));
      }
      this.showMenu();
    });
  }

  async addContact() {
    console.log(this.color('\n--- AGREGAR NUEVO CONTACTO ---', colors.magenta));
    
    const name = await this.question('Nombre completo: ');
    const phone = await this.question('Teléfono (ej: +54 9 260 123-4567): ');
    const email = await this.question('Email: ');
    const address = await this.question('Dirección (opcional): ');

    try {
      const contact = await this.manager.addContact(name, phone, email, address);
      console.log(this.color('\n✅ Contacto agregado exitosamente:', colors.green));
      console.log(this.color(contact.toString(), colors.white));
    } catch (error) {
      console.log(this.color(`\n❌ Error: ${error.message}`, colors.red));
    }
  }

  async searchContact() {
    const query = await this.question('\nBuscar (nombre, teléfono, email): ');
    const results = this.manager.searchContacts(query);

    if (results.length === 0) {
      console.log(this.color('⚠️ No se encontraron contactos', colors.yellow));
      return;
    }

    console.log(this.color(`\n✅ Encontrados ${results.length} contacto(s):`, colors.green));
    results.forEach((contact, i) => {
      console.log(this.color(`\n${i + 1}. ${contact.toString()}`, colors.white));
      console.log(this.color(`   ID: ${contact.id}`, colors.gray || colors.cyan));
    });
  }

  async listContacts() {
    const contacts = this.manager.getAllContacts();
    
    if (contacts.length === 0) {
      console.log(this.color('📭 Tu agenda está vacía', colors.yellow));
      return;
    }

    console.log(this.color(`\n✅ Tienes ${contacts.length} contacto(s):\n`, colors.green));
    contacts.forEach((contact, i) => {
      console.log(this.color(`${i + 1}. ${contact.toString()}`, colors.white));
    });
  }

  async editContact() {
    const id = await this.question('\nID del contacto a editar: ');
    const contact = this.manager.getContactById(id);

    if (!contact) {
      console.log(this.color('❌ Contacto no encontrado', colors.red));
      return;
    }

    console.log(this.color(`\nEditando: ${contact.name}`, colors.cyan));
    console.log(this.color(`Actual: ${contact.phone} | ${contact.email}`, colors.gray || colors.cyan));

    const name = await this.question(`Nuevo nombre (${contact.name}): `) || contact.name;
    const phone = await this.question(`Nuevo teléfono (${contact.phone}): `) || contact.phone;
    const email = await this.question(`Nuevo email (${contact.email}): `) || contact.email;
    const address = await this.question(`Nueva dirección (${contact.address || 'vacío'}): `) || contact.address;

    try {
      const updated = await this.manager.updateContact(id, { name, phone, email, address });
      console.log(this.color('\n✅ Contacto actualizado:', colors.green));
      console.log(this.color(updated.toString(), colors.white));
    } catch (error) {
      console.log(this.color(`\n❌ Error: ${error.message}`, colors.red));
    }
  }

  async deleteContact() {
    const id = await this.question('\nID del contacto a eliminar: ');
    const contact = this.manager.getContactById(id);

    if (!contact) {
      console.log(this.color('❌ Contacto no encontrado', colors.red));
      return;
    }

    const confirm = await this.question(
      this.color(`¿Eliminar a ${contact.name}? (s/n): `, colors.yellow)
    );

    if (confirm.toLowerCase() !== 's') {
      console.log(this.color('Operación cancelada', colors.blue));
      return;
    }

    try {
      await this.manager.deleteContact(id);
      console.log(this.color(`✅ ${contact.name} eliminado correctamente`, colors.green));
    } catch (error) {
      console.log(this.color(`\n❌ Error: ${error.message}`, colors.red));
    }
  }

  question(query) {
    return new Promise(resolve => this.rl.question(query, resolve));
  }
}

// Iniciar la CLI
const cli = new CLI();
cli.start().catch(console.error);