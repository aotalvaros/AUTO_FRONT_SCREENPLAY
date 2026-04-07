# language: es
@HU-010
Característica: Sidebar de Navegación en CyberGuard
  Como usuario autenticado del sistema CyberGuard
  Quiero un menú lateral de navegación funcional
  Para navegar entre las diferentes secciones del sistema

  @smoke @HU-010
  Escenario: El sidebar es visible después de iniciar sesión
    Dado que el administrador ha iniciado sesión en CyberGuard
    Entonces el sidebar de navegación es visible

  @smoke @HU-010
  Escenario: El ítem "Gestión Usuarios" es visible para el administrador
    Dado que el administrador ha iniciado sesión en CyberGuard
    Entonces el sidebar contiene el ítem de navegación "Usuarios"

  @interaccion @HU-010
  Escenario: El administrador puede colapsar el menú lateral
    Dado que el administrador ha iniciado sesión en CyberGuard
    Cuando colapsa el menú lateral
    Entonces el menú lateral está colapsado

  @interaccion @HU-010
  Escenario: El administrador puede expandir el menú lateral colapsado
    Dado que el administrador ha iniciado sesión en CyberGuard
    Cuando colapsa el menú lateral
    Y expande el menú lateral
    Entonces el menú lateral está expandido

  @persistencia @HU-010
  Escenario: El estado colapsado del sidebar persiste al recargar la página
    Dado que el administrador ha iniciado sesión en CyberGuard
    Cuando colapsa el menú lateral
    Y recarga la página
    Entonces el menú lateral sigue colapsado

  @interaccion @HU-010
  Escenario: El ítem activo se resalta al navegar entre secciones
    Dado que el administrador ha iniciado sesión en CyberGuard
    Cuando navega a la página de incidentes
    Entonces el ítem "Incidentes" está resaltado como activo en el sidebar
