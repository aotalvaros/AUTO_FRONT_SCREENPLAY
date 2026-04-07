# language: es
@HU-008
Característica: Gestión de Usuarios en CyberGuard
  Como administrador del sistema CyberGuard
  Quiero gestionar los usuarios del sistema
  Para mantener el control de acceso y permisos de la plataforma

  Antecedentes:
    Dado que el administrador ha iniciado sesión en CyberGuard

  @smoke @HU-008
  Escenario: Admin accede a la página de gestión de usuarios
    Cuando navega a la página de gestión de usuarios
    Entonces la tabla de usuarios es visible con al menos un registro

  @smoke @HU-008
  Escenario: Creación exitosa de un usuario con datos válidos
    Cuando navega a la página de gestión de usuarios
    Y crea un nuevo usuario con datos válidos generados dinámicamente
    Entonces se muestra un mensaje de éxito al crear el usuario
    Y el nuevo usuario aparece en la tabla de usuarios

  @negativo @validacion @HU-008
  Escenario: Sistema rechaza la creación de usuario con email duplicado
    Cuando navega a la página de gestión de usuarios
    Y intenta crear un usuario con el email "admin@cyberguard.com" ya existente
    Entonces se muestra un mensaje de error en la gestión de usuarios

  @proteccion-propia-cuenta @HU-008
  Escenario: El admin no ve botones de acción para su propia cuenta
    Cuando navega a la página de gestión de usuarios
    Entonces no se muestran botones de acción para el usuario "admin@cyberguard.com"

  @toggle-status @HU-008
  Escenario: Admin desactiva y reactiva un usuario existente
    Cuando navega a la página de gestión de usuarios
    Y cambia el estado del usuario "incident.handler@cyberguard.com"
    Entonces el estado del usuario "incident.handler@cyberguard.com" ha cambiado
    Cuando cambia nuevamente el estado del usuario "incident.handler@cyberguard.com"
    Entonces el usuario "incident.handler@cyberguard.com" está activo nuevamente
