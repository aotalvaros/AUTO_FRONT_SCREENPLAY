# language: es

Característica: Gestión de amenazas de seguridad en CyberGuard System

@ReporteAmenaza @Positivo
  Escenario: Reporte exitoso de una amenaza con datos válidos
    Dado que el analista ha iniciado sesión en CyberGuard System
    Cuando reporta una amenaza de tipo "Malware" con severidad "Alta" y descripción "Ransomware detectado en servidor de producción"
    Entonces el sistema confirma que la amenaza fue registrada exitosamente
    Y el mensaje de confirmación incluye el identificador de la amenaza

@ReporteAmenaza @Negativo
  Escenario: Intento fallido de reporte con campos obligatorios vacíos
    Dado que el analista ha iniciado sesión en CyberGuard System
    Cuando intenta reportar una amenaza sin completar los campos obligatorios
    Entonces el sistema muestra mensajes de validación indicando los campos requeridos
    Y el reporte no es enviado al sistema
