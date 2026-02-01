#!/usr/bin/env python3
"""
Gestor de Contactos - CLI Interactiva
"""

import os
import sys
from services.contact_manager import ContactManager


# Colores ANSI para terminal
class Colors:
    RESET = "\033[0m"
    RED = "\033[31m"
    GREEN = "\033[32m"
    YELLOW = "\033[33m"
    BLUE = "\033[34m"
    CYAN = "\033[36m"
    MAGENTA = "\033[35m"
    BOLD = "\033[1m"
    BG_BLUE = "\033[44m"
    BG_GREEN = "\033[42m"
    WHITE = "\033[37m"


def cprint(text: str, color: str = Colors.RESET) -> None:
    """Imprime texto con color"""
    print(f"{color}{text}{Colors.RESET}")


def clear_screen() -> None:
    """Limpia la pantalla de la terminal"""
    os.system("cls" if os.name == "nt" else "clear")


def wait_for_enter() -> None:
    """Espera a que el usuario presione ENTER sin validaciones"""
    try:
        input("\nPresione ENTER para continuar...")
    except (KeyboardInterrupt, EOFError):
        print()
        pass


def show_menu() -> None:
    """Muestra el menú principal"""
    cprint("\n" + "📱" * 25, Colors.CYAN)
    cprint(f"{Colors.BOLD}        GESTOR DE CONTACTOS v1.0        {Colors.RESET}", Colors.BG_BLUE + Colors.WHITE)
    cprint("📱" * 25, Colors.CYAN)
    cprint("\n1. ➕ Agregar contacto", Colors.GREEN)
    cprint("2. 🔍 Buscar contacto", Colors.YELLOW)
    cprint("3. 👁️  Ver todos los contactos", Colors.BLUE)
    cprint("4. ✏️  Editar contacto", Colors.MAGENTA)
    cprint("5. 🗑️  Eliminar contacto", Colors.RED)
    cprint("6. ❌ Salir", Colors.RED)
    cprint("📱" * 25, Colors.CYAN)


def get_input(prompt: str, required: bool = True) -> str:
    """Obtiene entrada del usuario con validación"""
    while True:
        try:
            value = input(f"\n{prompt}").strip()
            if value or not required:
                return value
            cprint("⚠️ Este campo es obligatorio", Colors.RED)
        except EOFError:
            return ""
        except KeyboardInterrupt:
            print()
            sys.exit(0)


def add_contact_flow(manager: ContactManager) -> None:
    """Flujo para agregar un contacto"""
    clear_screen()
    cprint("\n--- ➕ AGREGAR NUEVO CONTACTO ---", Colors.GREEN)

    try:
        name = get_input("Nombre completo: ")
        phone = get_input("Teléfono (ej: +54 9 260 123-4567): ")
        email = get_input("Email: ")
        address = get_input("Dirección (opcional): ", required=False)

        contact = manager.add_contact(name, phone, email, address)
        cprint("\n✅ Contacto agregado exitosamente:", Colors.GREEN)
        cprint(f"   {contact}", Colors.CYAN)
    except ValueError as e:
        cprint(f"\n❌ Error: {e}", Colors.RED)
    except Exception as e:
        cprint(f"\n❌ Error inesperado: {e}", Colors.RED)


def search_contact_flow(manager: ContactManager) -> None:
    """Flujo para buscar contactos"""
    clear_screen()
    cprint("\n--- 🔍 BUSCAR CONTACTO ---", Colors.YELLOW)

    query = get_input("Buscar (nombre, teléfono, email o dirección): ", required=False)
    if not query:
        results = manager.get_all_contacts()
        cprint("\n📋 Mostrando todos los contactos:", Colors.BLUE)
    else:
        results = manager.search_contacts(query)
        cprint(f"\n✅ Encontrados {len(results)} resultado(s) para '{query}':", Colors.GREEN)

    if not results:
        cprint("📭 No se encontraron contactos", Colors.YELLOW)
        wait_for_enter()
        return

    for i, contact in enumerate(results, 1):
        cprint(f"\n{i}. {contact}", Colors.CYAN)
        cprint(f"   ID: {contact.id}", Colors.BLUE)
    wait_for_enter()


def list_contacts_flow(manager: ContactManager) -> None:
    """Muestra todos los contactos"""
    clear_screen()
    contacts = manager.get_all_contacts()

    cprint("\n--- 👁️ TODOS LOS CONTACTOS ---", Colors.BLUE)

    if not contacts:
        cprint("📭 Tu agenda está vacía", Colors.YELLOW)
        wait_for_enter()
        return

    cprint(f"\n✅ Tienes {len(contacts)} contacto(s):\n", Colors.GREEN)
    for i, contact in enumerate(contacts, 1):
        cprint(f"{i}. {contact}", Colors.CYAN)
    cprint(f"\nTotal: {len(contacts)} contactos", Colors.BOLD + Colors.BLUE)
    wait_for_enter()


def edit_contact_flow(manager: ContactManager) -> None:
    """Flujo para editar un contacto"""
    clear_screen()
    cprint("\n--- ✏️ EDITAR CONTACTO ---", Colors.MAGENTA)

    contact_id = get_input("ID del contacto a editar: ")
    contact = manager.get_contact_by_id(contact_id)

    if not contact:
        cprint("❌ Contacto no encontrado", Colors.RED)
        wait_for_enter()
        return

    cprint(f"\nEditando: {contact.name}", Colors.CYAN)
    cprint(f"Actual: {contact.phone} | {contact.email}", Colors.BLUE)

    try:
        name = get_input(f"Nuevo nombre ({contact.name}): ", required=False) or contact.name
        phone = get_input(f"Nuevo teléfono ({contact.phone}): ", required=False) or contact.phone
        email = get_input(f"Nuevo email ({contact.email}): ", required=False) or contact.email
        address = get_input(f"Nueva dirección ({contact.address or 'vacío'}): ", required=False) or contact.address

        updated = manager.update_contact(
            contact_id,
            name=name,
            phone=phone,
            email=email,
            address=address,
        )
        cprint("\n✅ Contacto actualizado exitosamente:", Colors.GREEN)
        cprint(f"   {updated}", Colors.CYAN)
    except ValueError as e:
        cprint(f"\n❌ Error: {e}", Colors.RED)
    wait_for_enter()


def delete_contact_flow(manager: ContactManager) -> None:
    """Flujo para eliminar un contacto"""
    clear_screen()
    cprint("\n--- 🗑️ ELIMINAR CONTACTO ---", Colors.RED)

    contact_id = get_input("ID del contacto a eliminar: ")
    contact = manager.get_contact_by_id(contact_id)

    if not contact:
        cprint("❌ Contacto no encontrado", Colors.RED)
        wait_for_enter()
        return

    cprint(f"\n¿Eliminar a {contact.name}?", Colors.YELLOW)
    cprint(f"   {contact}", Colors.CYAN)

    confirm = get_input("\nEscribe 'SI' para confirmar: ", required=False)
    if confirm.upper() != "SI":
        cprint("Operación cancelada", Colors.BLUE)
        wait_for_enter()
        return

    try:
        manager.delete_contact(contact_id)
        cprint(f"\n✅ {contact.name} eliminado correctamente", Colors.GREEN)
    except Exception as e:
        cprint(f"\n❌ Error: {e}", Colors.RED)
    wait_for_enter()


def main() -> None:
    """Función principal"""
    manager = ContactManager()

    while True:
        clear_screen()
        show_menu()

        option = get_input("\nSeleccione una opción (1-6): ")

        if option == "1":
            add_contact_flow(manager)
        elif option == "2":
            search_contact_flow(manager)
        elif option == "3":
            list_contacts_flow(manager)
        elif option == "4":
            edit_contact_flow(manager)
        elif option == "5":
            delete_contact_flow(manager)
        elif option == "6":
            clear_screen()
            cprint("\n👋 ¡Hasta pronto!", Colors.GREEN)
            cprint("Gracias por usar Gestor de Contactos 💙\n", Colors.BLUE)
            break
        else:
            cprint("⚠️ Opción inválida. Seleccione 1-6", Colors.RED)
            wait_for_enter()


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        clear_screen()
        cprint("\n👋 Operación cancelada por el usuario\n", Colors.YELLOW)
        sys.exit(0)
    except Exception as e:
        clear_screen()
        cprint(f"\n❌ Error fatal: {e}\n", Colors.RED)
        import traceback
        traceback.print_exc()
        sys.exit(1)