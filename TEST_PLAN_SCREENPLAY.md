# Plan de Pruebas — AUTO_FRONT_SCREENPLAY

**Proyecto:** CyberGuard Incident Response Management System  
**Módulo de automatización:** AUTO_FRONT_SCREENPLAY  
**Patrón:** Screenplay (Serenity BDD)  
**Versión:** 1.0  
**Fecha:** 6 de abril de 2026  
**Autor:** QA Automation (ASDD)

---

## 1. Contexto del Proyecto

### 1.1 Sistema bajo prueba

**CyberGuard** es un sistema de gestión de respuesta a incidentes de ciberseguridad. El frontend es una aplicación Angular 21 (Standalone/Signals/Zoneless) servida en `http://localhost:4200` que se comunica con un backend Express en `http://localhost:3000` y utiliza Firebase Authentication para la gestión de identidades.

### 1.2 Objetivo de este módulo de automatización

Validar el comportamiento **observable desde el navegador** de las tres historias de usuario IRMS (HU-001, HU-008, HU-010) usando el **patrón Screenplay**, donde un Actor realiza Tareas (Tasks) y hace Preguntas (Questions) sobre el estado del sistema, abstrayendo la interacción del navegador a un nivel de intención del usuario.

### 1.3 Diferencia con AUTO_FRONT_POM_FACTORY

| Aspecto | POM Factory | Screenplay |
|---------|-------------|------------|
| **Abstracción** | El test llama métodos de una página (`userPage.clickNewUserButton()`) | El actor _intenta_ tareas (`actor.attemptsTo(CreateUser.withData(...))`) |
| **Nivel semántico** | HTML/DOM — "haz clic en el selector X" | Comportamiento — "crea un usuario con estos datos" |
| **Reutilización** | Por herencia de PageObject | Por composición de Tasks |
| **Estado compartido** | Variables de instancia en StepDefs | `actor.remember()` / `actor.recall()` — el Stage es el contexto |
| **Lecturas** | Getters en el Page (`page.getSuccessMessage()`) | Questions (`actor.asksFor(SuccessMessage.displayed())`) |

**Ambos repos cubren los MISMOS flujos E2E.** La diferencia es el nivel de abstracción del código de automatización, no del alcance funcional.

### 1.4 Alcance

| Alcance | Incluido |
|---------|----------|
| Validación de flujos E2E de UI con patrón Screenplay | ✅ |
| Verificación de reglas de negocio visibles en pantalla | ✅ |
| Tests de integración frontend–backend en navegador real | ✅ |
| Validación de estilos CSS / visual regression | ❌ |
| Tests de performance / carga | ❌ |
| Comportamiento responsive / mobile | ❌ |

---

## 2. Stack Tecnológico

| Componente | Tecnología | Versión |
|------------|-----------|---------|
| Lenguaje | Java | 17 |
| Framework de pruebas | Serenity BDD | 4.2.12 |
| Patrón de diseño | Screenplay (Tasks + Questions + Targets) | — |
| DSL de escenarios | Cucumber | 7.20.1 |
| Motor de ejecución | JUnit Platform | 1.11.4 |
| Automatización de navegador | Selenium WebDriver (vía Serenity) | incluido |
| Aserciones | AssertJ | 3.27.3 |
| Build tool | Gradle | 8.x |
| Reporte | Serenity Single-Page HTML | incluido |

---

## 3. Arquitectura del Módulo

```
src/test/java/com/cyberguard/automation/
├── hooks/
│   └── ScreenplayHooks.java              ← @Before: setTheStage / @After: drawTheCurtain
│
├── ui/                                    ← TARGETS (selectores CSS encapsulados)
│   ├── LoginForm.java                     ← Target: #username, #password, button[type=submit]
│   ├── CyberGuardLoginPage.java           ← PageObject con @DefaultUrl("/autenticacion")
│   ├── DashboardTargets.java              ← Target: .dashboard-container, h1, .dashboard-main
│   ├── SidebarTargets.java                ← Target: .sidebar, .sidebar__toggle, .sidebar__link
│   ├── IncidentTargets.java               ← Target: .toolbar .btn-primary, .form-card, .data-table
│   └── UserManagementTargets.java         ← Target: .toolbar .btn-primary, .data-table, .btn-edit
│
├── tasks/                                 ← TASKS (acciones del actor)
│   ├── Authenticate.java                  ← Existente — login con credenciales
│   ├── NavigateTo.java                    ← Navegar a /incidents, /users vía sidebar
│   ├── CollapseSidebar.java               ← Clic en .sidebar__toggle cuando expandido
│   ├── ExpandSidebar.java                 ← Clic en .sidebar__toggle cuando colapsado
│   ├── CreateIncidentFromThreat.java      ← Abrir form → ingresar threatId → submit
│   ├── CreateUser.java                    ← Abrir form → llenar campos → submit
│   └── ToggleUserStatus.java              ← Clic en btn-deactivate/activate → confirm dialog
│
├── questions/                             ← QUESTIONS (verificaciones del actor)
│   ├── AlertsVisible.java                 ← Existente
│   ├── SuccessMessage.java                ← Existente
│   ├── ValidationMessages.java            ← Existente
│   ├── DashboardDisplayed.java            ← ¿.dashboard-container visible?
│   ├── SidebarState.java                  ← ¿Colapsado? ¿Expandido? ¿Ítem activo?
│   ├── CreateButtonVisible.java           ← ¿.toolbar .btn-primary visible en /incidents?
│   ├── UserInTable.java                   ← ¿username aparece en .data-table?
│   ├── UserStatusBadge.java               ← ¿Estado del usuario (ACTIVO/INACTIVO)?
│   ├── ActionButtonsVisibleForUser.java   ← ¿Botones edit/toggle visibles para un username?
│   └── IncidentTableHasRecords.java       ← ¿Tabla de incidentes tiene al menos N filas?
│
├── util/
│   └── TestData.java                      ← Credenciales, emails dinámicos, UUIDs
│
├── stepdefinitions/
│   ├── RegistroAmenazaStepDefinitions.java ← Existente
│   ├── SharedStepDefinitions.java          ← Login genérico, navegación compartida
│   ├── IncidentStepDefinitions.java        ← Steps de HU-001
│   ├── UserManagementStepDefinitions.java  ← Steps de HU-008
│   └── SidebarStepDefinitions.java         ← Steps de HU-010
│
├── runners/
│   ├── RegistroAmenazaRunner.java          ← Existente
│   ├── IncidentRunner.java                 ← features/incident_management.feature
│   ├── UserManagementRunner.java           ← features/user_management.feature
│   └── SidebarRunner.java                  ← features/sidebar_navigation.feature
│
└── resources/features/
    ├── registro.feature                    ← Existente (amenazas)
    ├── incident_management.feature         ← NUEVO — HU-001 (5 escenarios)
    ├── user_management.feature             ← NUEVO — HU-008 (5 escenarios)
    └── sidebar_navigation.feature          ← NUEVO — HU-010 (6 escenarios)
```

### 3.1 Flujo de datos del patrón Screenplay

```
Feature File (Gherkin)
    ↓
StepDefinition
    ↓
Actor.attemptsTo( Task )      ← "El actor INTENTA hacer algo"
    ↓
Task.performAs(actor)
    ↓
actor.attemptsTo(
    WaitUntil(Target, isVisible),    ← UI Target (selector CSS)
    Enter.theValue("...").into(Target),
    Click.on(Target)
)
    ↓
Actor.asksFor( Question )    ← "El actor PREGUNTA sobre el estado"
    ↓
Question.answeredBy(actor)
    ↓
Target.resolveFor(actor) → WebElement → getText(), isVisible()
    ↓
AssertJ assertion
```

---

## 4. Historias de Usuario Cubiertas

### HU-001 — Gestión de Incidentes desde Amenazas

**Criterios de aceptación validados por E2E:**
- Admin accede a la lista de incidentes ✅
- Admin crea incidente desde amenaza crítica ✅ (requiere datos previos)
- Usuario sin permiso no ve botón "Crear Incidente" 🔒 (requiere Firebase)
- Error con UUID inexistente ✅
- Filtros de estado y severidad visibles ✅

### HU-008 — Gestión de Usuarios

**Criterios de aceptación validados por E2E:**
- Admin accede a la página de gestión de usuarios ✅
- Admin crea usuario con datos válidos ✅
- Rechazo de email duplicado ✅
- Admin no ve botones de acción para su propia cuenta ✅
- Admin desactiva y reactiva un usuario existente ✅

### HU-010 — Sidebar de Navegación

**Criterios de aceptación validados por E2E:**
- Sidebar visible después del login ✅
- Ítem "Gestión Usuarios" visible para admin ✅
- Colapsar menú lateral ✅
- Expandir menú lateral colapsado ✅
- Estado colapsado persiste al recargar ✅
- Ítem activo se resalta al navegar ✅

---

## 5. Feature Files — Escenarios Gherkin

### 5.1 `incident_management.feature` — 5 escenarios

| # | Tag | Escenario | Tasks usadas | Questions usadas |
|---|-----|-----------|-------------|-----------------|
| 1 | `@smoke` | Admin accede a lista de incidentes | `Authenticate`, `NavigateTo` | `CreateButtonVisible` |
| 2 | `@smoke @requiere-datos-previos` | Admin crea incidente desde amenaza crítica | `Authenticate`, `NavigateTo`, `CreateIncidentFromThreat` | `SuccessMessage`, `IncidentTableHasRecords` |
| 3 | `@pendiente-firebase` | Formulario no visible para usuario sin permiso | `Authenticate` (incident_handler) | `CreateButtonVisible` (expected false) |
| 4 | `@validacion` | Error con UUID inexistente | `Authenticate`, `CreateIncidentFromThreat` | `AlertsVisible` (error) |
| 5 | `@filtros` | Filtros disponibles en la página | `Authenticate`, `NavigateTo` | Target `.filters-bar` visible |

### 5.2 `user_management.feature` — 5 escenarios

| # | Tag | Escenario | Tasks usadas | Questions usadas |
|---|-----|-----------|-------------|-----------------|
| 1 | `@smoke` | Admin accede a gestión de usuarios | `Authenticate`, `NavigateTo` | `UserInTable` |
| 2 | `@smoke` | Creación de usuario con datos válidos | `Authenticate`, `CreateUser` | `SuccessMessage`, `UserInTable` |
| 3 | `@validacion` | Rechazo de email duplicado | `Authenticate`, `CreateUser` | `AlertsVisible` (error) |
| 4 | `@proteccion-propia-cuenta` | Admin no ve botones para sí mismo | `Authenticate`, `NavigateTo` | `ActionButtonsVisibleForUser` (false) |
| 5 | `@toggle-status` | Desactivar y reactivar usuario | `Authenticate`, `ToggleUserStatus` | `UserStatusBadge` |

### 5.3 `sidebar_navigation.feature` — 6 escenarios

| # | Tag | Escenario | Tasks usadas | Questions usadas |
|---|-----|-----------|-------------|-----------------|
| 1 | `@smoke` | Sidebar visible tras login | `Authenticate` | `SidebarState` (visible) |
| 2 | `@smoke` | Ítem "Gestión Usuarios" visible para admin | `Authenticate` | `SidebarState` (ítem presente) |
| 3 | `@interaccion` | Colapsar menú lateral | `Authenticate`, `CollapseSidebar` | `SidebarState` (collapsed) |
| 4 | `@interaccion` | Expandir menú colapsado | `Authenticate`, `CollapseSidebar`, `ExpandSidebar` | `SidebarState` (expanded) |
| 5 | `@persistencia` | Estado colapsado persiste al recargar | `Authenticate`, `CollapseSidebar` | `SidebarState` (collapsed after reload) |
| 6 | `@interaccion` | Ítem activo se resalta al navegar | `Authenticate`, `NavigateTo` | `SidebarState` (active item) |

---

## 6. Inventario de Artefactos Screenplay

### 6.1 UI Targets (selectores CSS)

| Clase | Target | Selector CSS | Componente Angular |
|-------|--------|-------------|-------------------|
| `LoginForm` | `USERNAME_FIELD` | `#username` | login |
| `LoginForm` | `PASSWORD_FIELD` | `#password` | login |
| `LoginForm` | `LOGIN_BUTTON` | `button[type='submit']` | login |
| `DashboardTargets` | `CONTAINER` | `.dashboard-container` | dashboard |
| `DashboardTargets` | `TITLE` | `.dashboard-header h1` | dashboard |
| `SidebarTargets` | `SIDEBAR` | `.sidebar` | sidebar |
| `SidebarTargets` | `TOGGLE_BUTTON` | `.sidebar__toggle` | sidebar |
| `SidebarTargets` | `NAV_ITEMS` | `.sidebar__item` | sidebar |
| `SidebarTargets` | `NAV_LINK` | `.sidebar__link` | sidebar |
| `SidebarTargets` | `ACTIVE_LINK` | `.sidebar__link--active` | sidebar |
| `SidebarTargets` | `NAV_LABEL` | `.nav-label` | sidebar |
| `IncidentTargets` | `PAGE_TITLE` | `.page-header h1` | incident-list |
| `IncidentTargets` | `CREATE_BUTTON` | `.toolbar .btn-primary` | incident-list |
| `IncidentTargets` | `FORM_CARD` | `.form-card` | incident-list |
| `IncidentTargets` | `THREAT_ID_INPUT` | `input[formControlName="threatId"]` | incident-list |
| `IncidentTargets` | `SUBMIT_BUTTON` | `.form-actions .btn-primary` | incident-list |
| `IncidentTargets` | `DATA_TABLE` | `.data-table` | incident-list |
| `IncidentTargets` | `TABLE_ROWS` | `.data-table tbody tr` | incident-list |
| `IncidentTargets` | `TABLE_CONTAINER` | `.table-container` | incident-list |
| `IncidentTargets` | `FILTERS_BAR` | `.filters-bar` | incident-list |
| `IncidentTargets` | `SUCCESS_ALERT` | `.alert.alert-success` | incident-list |
| `IncidentTargets` | `ERROR_ALERT` | `.alert.alert-error` | incident-list |
| `UserMgmtTargets` | `PAGE_TITLE` | `.page-header h1` | user-management |
| `UserMgmtTargets` | `NEW_USER_BUTTON` | `.toolbar .btn-primary` | user-management |
| `UserMgmtTargets` | `FORM_CARD` | `.form-card` | user-management |
| `UserMgmtTargets` | `EMAIL_INPUT` | `input[formControlName="email"]` | user-management |
| `UserMgmtTargets` | `FULLNAME_INPUT` | `input[formControlName="fullName"]` | user-management |
| `UserMgmtTargets` | `USERNAME_INPUT` | `input[formControlName="username"]` | user-management |
| `UserMgmtTargets` | `ROLE_SELECT` | `select[formControlName="role"]` | user-management |
| `UserMgmtTargets` | `SUBMIT_BUTTON` | `.form-actions .btn-primary` | user-management |
| `UserMgmtTargets` | `DATA_TABLE` | `.data-table` | user-management |
| `UserMgmtTargets` | `TABLE_CONTAINER` | `.table-container` | user-management |
| `UserMgmtTargets` | `USER_ROWS` | `.data-table tbody tr` | user-management |
| `UserMgmtTargets` | `SUCCESS_ALERT` | `.alert.alert-success` | user-management |
| `UserMgmtTargets` | `ERROR_ALERT` | `.alert.alert-error` | user-management |
| `UserMgmtTargets` | `ACTIVE_BADGE` | `.badge.badge-active` | user-management |
| `UserMgmtTargets` | `INACTIVE_BADGE` | `.badge.badge-inactive` | user-management |
| `UserMgmtTargets` | `TU_CUENTA_BADGE` | `.badge.badge-info` | user-management |
| `UserMgmtTargets` | `EDIT_BUTTON` | `.btn-sm.btn-edit` | user-management |
| `UserMgmtTargets` | `DEACTIVATE_BTN` | `.btn-sm.btn-deactivate` | user-management |
| `UserMgmtTargets` | `ACTIVATE_BTN` | `.btn-sm.btn-activate` | user-management |

### 6.2 Tasks

| Clase | Método estático | Qué hace | Targets implicados |
|-------|----------------|----------|-------------------|
| `Authenticate` | `withCredentials(email, pass)` | Login en `/autenticacion` | `LoginForm.*` |
| `NavigateTo` | `theIncidentsPage()` | Clic en ítem sidebar "Incidentes" | `SidebarTargets.NAV_LINK` |
| `NavigateTo` | `theUsersPage()` | Clic en ítem sidebar "Gestión Usuarios" | `SidebarTargets.NAV_LINK` |
| `CollapseSidebar` | `now()` | Clic en toggle cuando expandido | `SidebarTargets.TOGGLE_BUTTON` |
| `ExpandSidebar` | `now()` | Clic en toggle cuando colapsado | `SidebarTargets.TOGGLE_BUTTON` |
| `CreateIncidentFromThreat` | `withThreatId(uuid)` | Abrir form → ingresar UUID → submit | `IncidentTargets.CREATE_BUTTON`, `.THREAT_ID_INPUT`, `.SUBMIT_BUTTON` |
| `CreateUser` | `withData(email, fullName, username, role)` | Abrir form → llenar campos → submit | `UserMgmtTargets.NEW_USER_BUTTON`, `.*_INPUT`, `.SUBMIT_BUTTON` |
| `ToggleUserStatus` | `forUser(username)` | Buscar fila → clic toggle → confirm dialog | `UserMgmtTargets.DEACTIVATE_BTN` / `.ACTIVATE_BTN` |

### 6.3 Questions

| Clase | Método estático | Retorna | Uso en aserción |
|-------|----------------|---------|-----------------|
| `DashboardDisplayed` | `isVisible()` | `Boolean` | Post-login: verifica que llegó al dashboard |
| `SidebarState` | `isCollapsed()` | `Boolean` | Después de colapsar/expandir |
| `SidebarState` | `hasItem(text)` | `Boolean` | Verificar que "Gestión Usuarios" está en el menú |
| `SidebarState` | `activeItemText()` | `String` | Verificar qué ítem está activo |
| `CreateButtonVisible` | `onIncidentsPage()` | `Boolean` | Verificar permiso de creación por rol |
| `UserInTable` | `withUsername(username)` | `Boolean` | Después de crear usuario |
| `UserStatusBadge` | `forUser(username)` | `String` | "ACTIVO" / "INACTIVO" después de toggle |
| `ActionButtonsVisibleForUser` | `withUsername(username)` | `Boolean` | Protección propia cuenta (false para admin) |
| `IncidentTableHasRecords` | `atLeast(n)` | `Boolean` | Después de crear incidente |

---

## 7. Datos de Prueba

### 7.1 Credenciales

| Constante | Valor | Rol | Firebase |
|-----------|-------|-----|----------|
| `ADMIN_EMAIL` | `admin@cyberguard.com` | admin | ✅ |
| `ADMIN_PASSWORD` | `AdminSofka123456` | — | ✅ |
| `HANDLER_EMAIL` | `incident.handler@cyberguard.com` | incident_handler | ✅ |
| `HANDLER_PASSWORD` | `HandlerSofka123456` | — | ✅ |

### 7.2 Datos dinámicos

| Constante | Fórmula | Propósito |
|-----------|---------|-----------|
| `RUN_SUFFIX` | `System.currentTimeMillis()` | Evitar 409 por email duplicado |
| `NEW_USER_EMAIL` | `"sp.test." + RUN_SUFFIX + "@cyberguard.com"` | Email único por ejecución |
| `NEW_USER_USERNAME` | `"sp.test." + RUN_SUFFIX` | Username único por ejecución |
| `NON_EXISTENT_UUID` | `"00000000-0000-0000-0000-000000000000"` | UUID que no existe en threats |

### 7.3 Datos creados por Hook

| Hook | Cuándo | Qué crea | Almacenado en |
|------|--------|----------|---------------|
| `@Before("@requiere-datos-previos")` | Pre-escenario | Amenaza crítica vía `POST /api/threats` | `actor.remember("criticalThreatId", uuid)` |

---

## 8. Precondiciones de Ejecución

| Precondición | Verificación |
|--------------|-------------|
| Docker Compose levantado | `curl http://localhost:4200` → 200 |
| Backend respondiendo | `curl http://localhost:3000/api/health` → 200 |
| Firebase activo | Login exitoso con `admin@cyberguard.com` |
| PostgreSQL con seeds | Usuarios `admin`, `soc`, `incident.handler` en tabla `users` |
| Chrome/Chromium instalado | `which google-chrome` o `which chromium-browser` |

---

## 9. Estrategia de Ejecución

### 9.1 Ejecución completa

```bash
cd AUTO_FRONT_SCREENPLAY
./gradlew clean test aggregate
# Reporte: target/site/serenity/index.html
```

### 9.2 Ejecución por feature

```bash
# Solo HU-001
./gradlew test -Dcucumber.filter.tags="@HU-001"

# Solo smoke
./gradlew test -Dcucumber.filter.tags="@smoke"

# Excluir pendientes de Firebase
./gradlew test -Dcucumber.filter.tags="not @pendiente-firebase"
```

### 9.3 Tags definidos

| Tag | Significado | Ejecutable sin Firebase extra |
|-----|-------------|------------------------------|
| `@smoke` | Fluzo crítico mínimo | ✅ |
| `@positivo` | Camino feliz | ✅ |
| `@negativo` | Camino de error | ✅ |
| `@validacion` | Validación de datos | ✅ |
| `@interaccion` | Interacción de UI | ✅ |
| `@persistencia` | Persistencia de estado | ✅ |
| `@toggle-status` | Toggle activar/desactivar | ✅ |
| `@proteccion-propia-cuenta` | Admin no se modifica a sí mismo | ✅ |
| `@filtros` | Filtros de tabla | ✅ |
| `@requiere-datos-previos` | Necesita amenaza vía API | ✅ (Hook la crea) |
| `@pendiente-firebase` | Necesita cuenta Firebase adicional | ❌ |
| `@HU-001` | Gestión de Incidentes | ✅ |
| `@HU-008` | Gestión de Usuarios | ✅ |
| `@HU-010` | Sidebar de Navegación | ✅ |

---

## 10. Gaps y Limitaciones Conocidas

| Gap | Impacto | Mitigación |
|-----|---------|------------|
| Escenario `@pendiente-firebase` no ejecutable sin cuenta Firebase para `incident_handler` | No se valida permiso negativo E2E | Validado por tests unitarios de `canCreate()` en frontend |
| `window.confirm()` en toggle de usuario | Selenium requiere manejo explícito del alert nativo | Task `ToggleUserStatus` incluye `driver.switchTo().alert().accept()` |
| Race condition Angular: tabla vacía durante `loading=true` | Aserciones prematuras leen datos stale | Tasks incluyen `WaitUntil(.table-container, isVisible)` antes de preguntas |
| Emails únicos por ejecución pero no limpieza de BD | Acumulación de usuarios de prueba en PostgreSQL | Prefijo `sp.test.` permite identificar y limpiar manualmente |
| Selectores basados en `formControlName` | Si Angular cambia la estrategia de forms, se rompen | Documentado como deuda; preferible a placeholders que cambian con i18n |

---

## 11. Trazabilidad con POM Factory

Cada escenario de este módulo tiene un equivalente exacto en AUTO_FRONT_POM_FACTORY:

| Feature | Escenarios Screenplay | Escenarios POM | Equivalencia |
|---------|-----------------------|----------------|-------------|
| `incident_management.feature` | 5 | 5 | 1:1 |
| `user_management.feature` | 5 | 5 | 1:1 |
| `sidebar_navigation.feature` | 6 | 6 | 1:1 |
| **Total** | **16** | **16** | **100%** |

La diferencia es exclusivamente en la implementación del código de automatización (Screenplay vs POM), no en la cobertura funcional.

---

## 12. Criterios de Aceptación del Módulo

| Criterio | Métrica |
|----------|---------|
| Compilación exitosa | `./gradlew compileTestJava` → BUILD SUCCESSFUL |
| Escenarios ejecutables sin Firebase extra | ≥ 14 de 16 (excluyendo `@pendiente-firebase`) |
| Reporte Serenity generado | `target/site/serenity/index.html` existe |
| Patrón Screenplay respetado | Todos los steps usan `actor.attemptsTo()` / `actor.asksFor()` |
| Sin código de Selenium directo en StepDefinitions | `driver.findElement()` solo permitido en Tasks/Questions |
| Feature files en español | Gherkin con `# language: es` |

---

## 13. Orden de Implementación

| Fase | Artefactos | Dependencia |
|------|-----------|-------------|
| **1** | UI Targets (`SidebarTargets`, `IncidentTargets`, `UserMgmtTargets`, `DashboardTargets`) | Ninguna |
| **2** | `TestData.java` actualizado con credenciales y datos dinámicos | Ninguna |
| **3** | Tasks (`NavigateTo`, `CollapseSidebar`, `ExpandSidebar`, `CreateIncidentFromThreat`, `CreateUser`, `ToggleUserStatus`) | Fase 1 |
| **4** | Questions (`DashboardDisplayed`, `SidebarState`, `CreateButtonVisible`, `UserInTable`, `UserStatusBadge`, `ActionButtonsVisibleForUser`, `IncidentTableHasRecords`) | Fase 1 |
| **5** | Feature files (3 archivos `.feature`) | Ninguna |
| **6** | StepDefinitions (`SharedStepDefinitions`, `IncidentStepDefinitions`, `UserManagementStepDefinitions`, `SidebarStepDefinitions`) | Fases 3, 4, 5 |
| **7** | Runners (3 runners + actualización de hooks) | Fase 6 |
| **8** | Compilación y validación | Todas |
