const fs = require('fs').promises;
const path = require('path');
const Contact = require('../models/Contact');

class ContactManager {
  constructor(filePath = path.join(__dirname, '../../data/contacts.csv')) {
    this.filePath = filePath;
    this.contacts = [];
    this.headers = ['id', 'name', 'phone', 'email', 'address', 'createdAt', 'updatedAt'];
  }

  async init() {
    try {
      await fs.access(this.filePath);
    } catch {
      await fs.mkdir(path.dirname(this.filePath), { recursive: true });
      await fs.writeFile(this.filePath, `"${this.headers.join('","')}"\n`, 'utf8');
      console.log('✅ Archivo CSV creado en:', this.filePath);
    }
    await this.loadContacts();
  }

  async loadContacts() {
    try {
      const data = await fs.readFile(this.filePath, 'utf8');
      const lines = data.split('\n').filter(line => line.trim() && !line.startsWith('"id"'));
      
      this.contacts = lines.map(line => Contact.fromCSV(line));
      console.log(`📥 ${this.contacts.length} contactos cargados`);
    } catch (error) {
      console.error('❌ Error cargando contactos:', error.message);
      this.contacts = [];
    }
  }

  async saveContacts() {
    try {
      const csvContent = [
        `"${this.headers.join('","')}"`,
        ...this.contacts.map(contact => contact.toCSV())
      ].join('\n') + '\n';

      await fs.writeFile(this.filePath, csvContent, 'utf8');
      console.log(`💾 ${this.contacts.length} contactos guardados`);
    } catch (error) {
      console.error('❌ Error guardando contactos:', error.message);
      throw error;
    }
  }

  async addContact(name, phone, email, address = '') {
    const contact = new Contact(null, name, phone, email, address);
    const validation = contact.isValid();
    
    if (!validation.valid) {
      throw new Error('Validación fallida:\n- ' + validation.errors.join('\n- '));
    }

    const exists = this.contacts.some(c => 
      c.email === contact.email || c.phone === contact.phone
    );

    if (exists) {
      throw new Error('⚠️ Ya existe un contacto con este email o teléfono');
    }

    this.contacts.push(contact);
    await this.saveContacts();
    return contact;
  }

  searchContacts(query) {
    const q = query.toLowerCase().trim();
    return this.contacts.filter(contact => 
      contact.name.toLowerCase().includes(q) ||
      contact.phone.includes(q) ||
      contact.email.includes(q) ||
      contact.address.toLowerCase().includes(q)
    );
  }

  getAllContacts() {
    return [...this.contacts].sort((a, b) => a.name.localeCompare(b.name));
  }

  async updateContact(id, updates) {
    const contact = this.contacts.find(c => c.id === id);
    if (!contact) throw new Error('❌ Contacto no encontrado');

    contact.update(updates);
    const validation = contact.isValid();
    if (!validation.valid) {
      throw new Error('Validación fallida:\n- ' + validation.errors.join('\n- '));
    }

    await this.saveContacts();
    return contact;
  }

  async deleteContact(id) {
    const index = this.contacts.findIndex(c => c.id === id);
    if (index === -1) throw new Error('❌ Contacto no encontrado');

    const deleted = this.contacts.splice(index, 1)[0];
    await this.saveContacts();
    return deleted;
  }

  getContactById(id) {
    return this.contacts.find(c => c.id === id);
  }
}

module.exports = ContactManager;