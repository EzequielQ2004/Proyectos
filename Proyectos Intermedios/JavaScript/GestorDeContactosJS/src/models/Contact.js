class Contact {
  constructor(id, name, phone, email, address = '') {
    this.id = id || this.generateId();
    this.name = name.trim();
    this.phone = phone.trim();
    this.email = email.trim().toLowerCase();
    this.address = address.trim();
    this.createdAt = new Date().toISOString();
    this.updatedAt = new Date().toISOString();
  }

  isValid() {
    const errors = [];

    if (!this.name || this.name.length < 2) {
      errors.push('Nombre debe tener al menos 2 caracteres');
    }

    const phoneRegex = /^\+?[\d\s\-()]{7,15}$/;
    if (!this.phone || !phoneRegex.test(this.phone)) {
      errors.push('Teléfono inválido (ej: +54 9 260 123-4567)');
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.email || !emailRegex.test(this.email)) {
      errors.push('Email inválido');
    }

    return {
      valid: errors.length === 0,
      errors
    };
  }

  generateId() {
    return Date.now().toString(36) + Math.random().toString(36).substr(2, 5);
  }

  update({ name, phone, email, address }) {
    if (name) this.name = name.trim();
    if (phone) this.phone = phone.trim();
    if (email) this.email = email.trim().toLowerCase();
    if (address !== undefined) this.address = address.trim();
    this.updatedAt = new Date().toISOString();
  }

  toCSV() {
    return `"${this.id}","${this.name}","${this.phone}","${this.email}","${this.address}","${this.createdAt}","${this.updatedAt}"`;
  }

  static fromCSV(line) {
    const values = line.match(/"([^"]*)"|([^,]+)/g).map(val => 
      val.startsWith('"') ? val.slice(1, -1) : val.trim()
    );
    
    return new Contact(
      values[0], // id
      values[1], // name
      values[2], // phone
      values[3], // email
      values[4]  // address
    );
  }

  toString() {
    return `${this.name} | 📱 ${this.phone} | ✉️ ${this.email}${this.address ? ` | 📍 ${this.address}` : ''}`;
  }
}

module.exports = Contact;