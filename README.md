# AUTO_FRONT_SCREENPLAY - CyberGuard System

Automatización de pruebas Front-End para **CyberGuard System** utilizando el patrón **Screenplay** con **Serenity BDD**.

---

## Descripción

Este proyecto valida el flujo de gestión de amenazas de la plataforma CyberGuard System mediante dos escenarios independientes, distintos a los cubiertos por el proyecto POM:

| # | Escenario | Tipo |
|---|-----------|------|
| 1 | Reporte exitoso de una amenaza con datos válidos | Positivo |
| 2 | Intento fallido de reporte con campos obligatorios vacíos | Negativo |

---

## Arquitectura

```
AUTO_FRONT_SCREENPLAY/
├── build.gradle
├── settings.gradle
├── gradlew
├── gradle/wrapper/
└── src/
    └── test/
        ├── java/com/cyberguard/automation/
        │   ├── hooks/
        │   │   └── ScreenplayHooks.java           ← Setup OnStage / OnlineCast
        │   ├── questions/
        │   │   ├── AlertsVisible.java             ← ¿Hay alertas en el dashboard?
        │   │   ├── SuccessMessage.java            ← ¿Se muestra mensaje de éxito?
        │   │   └── ValidationMessages.java        ← ¿Se muestran errores de validación?
        │   ├── runners/
        │   │   └── RegistroAmenazaRunner.java     ← @Suite JUnit Platform
        │   ├── stepdefinitions/
        │   │   └── RegistroAmenazaStepDefinitions.java
        │   ├── tasks/
        │   │   ├── Authenticate.java              ← Ingresar credenciales y hacer login
        │   │   ├── ReportThreat.java              ← Llenar y enviar formulario de amenaza
        │   │   └── SubmitEmptyThreatForm.java     ← Enviar formulario sin datos
        │   ├── ui/
        │   │   ├── CyberGuardLoginPage.java       ← PageObject @DefaultUrl(/autenticacion)
        │   │   ├── DashboardAlerts.java           ← Targets del listado de alertas
        │   │   ├── LoginForm.java                 ← Targets del formulario de login
        │   │   └── ThreatReportForm.java          ← Targets del formulario de amenazas
        │   └── util/
        │       └── TestData.java                  ← Constantes de datos de prueba
        └── resources/
            ├── features/
            │   └── registro.feature
            ├── serenity.conf
            └── cucumber.properties
```

### Patrón utilizado

**Screenplay:** Organiza la automatización en torno a **Actores** que poseen **Habilidades** (abilities), ejecutan **Tareas** (tasks) compuestas de **Acciones** atómicas (interactions) y responden **Preguntas** (questions) sobre el estado del sistema. Cada `Task` aplica el principio de responsabilidad única (SRP).

| Componente | Clase(s) | Responsabilidad |
|------------|----------|----------------|
| `Actor` | (OnStage / OnlineCast) | Sujeto que ejecuta el flujo con BrowseTheWeb |
| `Task` | Authenticate, ReportThreat, SubmitEmptyThreatForm | Acción de negocio (SRP por clase) |
| `Question` | SuccessMessage, ValidationMessages, AlertsVisible | Consulta observable del estado de la UI |
| `Target` | LoginForm, ThreatReportForm, DashboardAlerts | Localizador de elemento web (en ui/) |
| `Hook` | ScreenplayHooks | Ciclo de vida del escenario (Before/After) |

---

## Stack Tecnológico

| Herramienta | Versión |
|-------------|---------|
| Java | 17 (OpenJDK) |
| Gradle | 8.12 |
| Serenity BDD | 4.2.12 |
| Serenity Screenplay | 4.2.12 |
| Serenity Screenplay WebDriver | 4.2.12 |
| Serenity Gradle Plugin | 5.3.7 |
| Cucumber | 7.20.1 |
| WebDriver | Chrome (autodownload) |
| IDE | VS Code / IntelliJ IDEA |
| AI Assistant | GitHub Copilot |

---

## Repositorio bajo prueba

Este proyecto automatiza pruebas sobre **CyberGuard System**:
> 🔗 [https://github.com/aotalvaros/cyberguard-system](https://github.com/aotalvaros/cyberguard-system)

---

## Prerequisitos

- **Java JDK 17+** instalado y configurado en `JAVA_HOME`
- **Google Chrome** instalado (el driver se descarga automáticamente)
- **CyberGuard System** clonado y corriendo localmente:
  ```bash
  git clone https://github.com/aotalvaros/cyberguard-system.git
  cd cyberguard-system
  sudo docker compose up --build
  ```
  Verificar que el frontend esté disponible en `http://localhost:4200`

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/aotalvaros/AUTO_FRONT_SCREENPLAY.git
   cd AUTO_FRONT_SCREENPLAY
   ```

2. Verificar que Gradle Wrapper esté disponible:
   ```bash
   ./gradlew --version
   ```

---

## Ejecución de Tests

### Ejecutar todos los tests y generar reporte
```bash
./gradlew clean test aggregate
```

### Ejecutar por tag específico
```bash
./gradlew clean test aggregate -Dcucumber.filter.tags="@positivo"
```

### Abrir el reporte Serenity (Linux)
```bash
xdg-open target/site/serenity/index.html
```

---

## Reportes

Tras la ejecución, Serenity BDD genera un reporte HTML detallado en:

```
target/site/serenity/index.html
```

El reporte incluye:
- Resultado de cada escenario (passed / failed / error)
- Capturas de pantalla por paso
- Tiempo de ejecución por escenario y tarea
- Detalle completo de interacciones de cada Actor con la UI

---

## Escenarios cubiertos

> Estos escenarios son **independientes entre sí** y distintos de los automatizados con POM (que cubren autenticación/login).

### Escenario 1 — Positivo: Reporte exitoso de una amenaza
Valida que un analista autenticado puede registrar una amenaza con tipo, severidad y descripción válidos, y que el sistema confirma el registro mostrándola en el dashboard.

### Escenario 2 — Negativo: Intento fallido con campos vacíos
Valida que al intentar enviar el formulario de reporte sin completar los campos obligatorios, el sistema muestra los mensajes de validación correspondientes y no procesa el reporte.
