import csv
import os
from datetime import datetime
from pathlib import Path
from typing import List, Optional
import uuid

from models.contact import Contact


class ContactManager:
    """Gestor de contactos con persistencia en CSV"""

    HEADERS = ["id", "name", "phone", "email", "address", "created_at", "updated_at"]

    def __init__(self, csv_path: str = None):
        # Determinar ruta absoluta del proyecto (raíz)
        if csv_path is None:
            # Ruta relativa desde este archivo: services/ -> raíz del proyecto
            project_root = Path(__file__).parent.parent
            self.csv_path = project_root / "data" / "contacts.csv"
        else:
            self.csv_path = Path(csv_path)

        self.contacts: List[Contact] = []
        self._ensure_data_directory()
        self.load_contacts()

    def _ensure_data_directory(self) -> None:
        """Crea el directorio de datos si no existe"""
        self.csv_path.parent.mkdir(parents=True, exist_ok=True)

        # Crea archivo CSV con headers si no existe
        if not self.csv_path.exists():
            with open(self.csv_path, "w", newline="", encoding="utf-8") as f:
                writer = csv.writer(f)
                writer.writerow(self.HEADERS)
            print(f"✅ Archivo CSV creado en: {self.csv_path.resolve()}")

    def load_contacts(self) -> None:
        """Carga contactos desde CSV"""
        self.contacts.clear()

        try:
            with open(self.csv_path, "r", encoding="utf-8") as f:
                reader = csv.reader(f)
                rows = list(reader)

                # Saltar header
                for row in rows[1:]:
                    if row and any(row):  # Evitar filas vacías
                        try:
                            contact = Contact.from_csv_row(row)
                            self.contacts.append(contact)
                        except Exception as e:
                            print(f"⚠️ Error cargando contacto {row[:3]}: {e}")

            print(f"📥 {len(self.contacts)} contactos cargados desde: {self.csv_path}")
        except FileNotFoundError:
            print(f"ℹ️ Archivo CSV no encontrado ({self.csv_path}), se creará al guardar el primer contacto")
        except Exception as e:
            print(f"❌ Error al cargar contactos: {e}")

    def save_contacts(self) -> bool:
        """Guarda contactos en CSV"""
        try:
            with open(self.csv_path, "w", newline="", encoding="utf-8") as f:
                writer = csv.writer(f)
                writer.writerow(self.HEADERS)
                for contact in self.contacts:
                    writer.writerow(contact.to_csv_row())

            print(f"💾 {len(self.contacts)} contactos guardados en: {self.csv_path}")
            return True
        except Exception as e:
            print(f"❌ Error al guardar contactos: {e}")
            return False

    def add_contact(self, name: str, phone: str, email: str, address: str = "") -> Contact:
        """Agrega un nuevo contacto"""
        # Generar ID único
        contact_id = str(uuid.uuid4())[:10]

        contact = Contact(
            id=contact_id,
            name=name,
            phone=phone,
            email=email,
            address=address,
        )

        # Validar
        is_valid, errors = contact.is_valid()
        if not is_valid:
            raise ValueError("\n".join(errors))

        # Verificar duplicados
        if any(c.email == contact.email or c.phone == contact.phone for c in self.contacts):
            raise ValueError("⚠️ Ya existe un contacto con este email o teléfono")

        self.contacts.append(contact)
        self.save_contacts()
        return contact

    def search_contacts(self, query: str) -> List[Contact]:
        """Busca contactos por nombre, email, teléfono o dirección"""
        query = query.lower().strip()
        if not query:
            return []

        return [
            contact
            for contact in self.contacts
            if (
                    query in contact.name.lower()
                    or query in contact.phone
                    or query in contact.email
                    or query in contact.address.lower()
            )
        ]

    def get_all_contacts(self) -> List[Contact]:
        """Retorna todos los contactos ordenados por nombre"""
        return sorted(self.contacts, key=lambda c: c.name.lower())

    def get_contact_by_id(self, contact_id: str) -> Optional[Contact]:
        """Obtiene un contacto por ID"""
        return next((c for c in self.contacts if c.id == contact_id), None)

    def update_contact(self, contact_id: str, **updates) -> Contact:
        """Actualiza un contacto existente"""
        contact = self.get_contact_by_id(contact_id)
        if not contact:
            raise ValueError("❌ Contacto no encontrado")

        # Aplicar actualizaciones
        contact.update(
            name=updates.get("name"),
            phone=updates.get("phone"),
            email=updates.get("email"),
            address=updates.get("address"),
        )

        # Validar
        is_valid, errors = contact.is_valid()
        if not is_valid:
            raise ValueError("\n".join(errors))

        self.save_contacts()
        return contact

    def delete_contact(self, contact_id: str) -> Contact:
        """Elimina un contacto por ID"""
        contact = self.get_contact_by_id(contact_id)
        if not contact:
            raise ValueError("❌ Contacto no encontrado")

        self.contacts.remove(contact)
        self.save_contacts()
        return contact