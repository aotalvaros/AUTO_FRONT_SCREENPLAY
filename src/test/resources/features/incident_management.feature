# language: es
@HU-001
Característica: Gestión de Incidentes desde Amenazas
  Como administrador del sistema CyberGuard
  Quiero gestionar incidentes creados a partir de amenazas críticas
  Para dar seguimiento efectivo a los incidentes de seguridad

  Antecedentes:
    Dado que el administrador ha iniciado sesión en CyberGuard

  @smoke @HU-001
  Escenario: Admin accede a la lista de incidentes
    Cuando navega a la página de incidentes
    Entonces el botón de crear incidente es visible en la página

  @smoke @requiere-datos-previos @HU-001
  Escenario: Admin crea un incidente a partir de una amenaza crítica
    Cuando navega a la página de incidentes
    Y crea un incidente a partir de la amenaza crítica registrada
    Entonces se muestra un mensaje de éxito al crear el incidente
    Y la tabla de incidentes contiene al menos 1 registro

  @pendiente-firebase @HU-001
  Escenario: Formulario de creación no disponible para usuario sin permiso
    Dado que el manejador de incidentes ha iniciado sesión en CyberGuard
    Cuando navega a la página de incidentes
    Entonces el botón de crear incidente no es visible en la página

  @negativo @validacion @HU-001
  Escenario: Error al intentar crear incidente con UUID inexistente
    Cuando navega a la página de incidentes
    Y intenta crear un incidente con el UUID "00000000-0000-0000-0000-000000000000"
    Entonces se muestra un mensaje de error en la página de incidentes

  @filtros @HU-001
  Escenario: Filtros de estado y severidad visibles en la página de incidentes
    Cuando navega a la página de incidentes
    Entonces la barra de filtros es visible en la página de incidentes
