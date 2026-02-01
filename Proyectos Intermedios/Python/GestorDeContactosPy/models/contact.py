import re
from datetime import datetime
from dataclasses import dataclass
import uuid


@dataclass
class Contact:
    id: str
    name: str
    phone: str
    email: str
    address: str = ""
    created_at: str = ""
    updated_at: str = ""

    def __post_init__(self):
        self.name = self.name.strip()
        self.phone = self.phone.strip()
        self.email = self.email.strip().lower()
        self.address = self.address.strip()

        if not self.created_at:
            self.created_at = datetime.now().isoformat()
        if not self.updated_at:
            self.updated_at = self.created_at

    def is_valid(self) -> tuple[bool, list[str]]:
        """Valida el contacto y retorna (es_valido, lista_errores)"""
        errors = []

        if not self.name or len(self.name) < 2:
            errors.append("❌ Nombre debe tener al menos 2 caracteres")

        phone_pattern = r"^\+?[\d\s\-()]{7,15}$"
        if not re.match(phone_pattern, self.phone):
            errors.append("❌ Teléfono inválido (ej: +54 9 260 123-4567)")

        email_pattern = r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
        if not re.match(email_pattern, self.email):
            errors.append("❌ Email inválido")

        return len(errors) == 0, errors

    def update(self, name: str = None, phone: str = None, email: str = None, address: str = None) -> None:
        """Actualiza los campos del contacto"""
        if name is not None:
            self.name = name.strip()
        if phone is not None:
            self.phone = phone.strip()
        if email is not None:
            self.email = email.strip().lower()
        if address is not None:
            self.address = address.strip()
        self.updated_at = datetime.now().isoformat()

    def to_csv_row(self) -> list[str]:
        """Convierte el contacto a lista para CSV"""
        return [
            self.id,
            self.name,
            self.phone,
            self.email,
            self.address,
            self.created_at,
            self.updated_at,
        ]

    @classmethod
    def from_csv_row(cls, row: list[str]):
        """Crea un contacto desde una fila CSV"""
        return cls(
            id=row[0],
            name=row[1],
            phone=row[2],
            email=row[3],
            address=row[4] if len(row) > 4 else "",
            created_at=row[5] if len(row) > 5 else "",
            updated_at=row[6] if len(row) > 6 else "",
        )

    def __str__(self) -> str:
        contact_str = f"👤 {self.name} | 📱 {self.phone} | ✉️ {self.email}"
        if self.address:
            contact_str += f" | 📍 {self.address}"
        return contact_str

    def __repr__(self) -> str:
        return f"<Contact id={self.id} name='{self.name}'>"