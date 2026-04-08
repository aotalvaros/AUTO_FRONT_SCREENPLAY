# Estructura del Proyecto AUTO_FRONT_SCREENPLAY

## Descripción General

Este proyecto implementa pruebas automatizadas de interfaz de usuario (UI) bajo el patrón
**Screenplay** utilizando **Serenity BDD** con **Cucumber** como test runner y **Gradle**
como gestor de dependencias. Está diseñado para validar flujos críticos de la aplicación
**CyberGuard System**.

---

## ¿Por qué Screenplay sobre POM?

| Aspecto | POM (Page Object Model) | Screenplay |
|---|---|---|
| **Enfoque** | Centrado en páginas | Centrado en actores y sus capacidades |
| **Responsabilidad** | Una clase por página | Una clase por tarea o pregunta |
| **Escalabilidad** | Difícil cuando crecen las páginas | Alta, cada tarea es independiente |
| **Principio SOLID** | Viola SRP cuando la página crece | SRP aplicado por diseño |
| **Legibilidad** | Técnica | Orientada al comportamiento del negocio |

---

## Árbol de Directorios

```
AUTO_FRONT_SCREENPLAY/
├── build.gradle
├── settings.gradle
├── serenity.conf
├── serenity.properties
├── STRUCTURE_EXPLANATION.md
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── cyberguard/
        │           └── automation/
        │               ├── hooks/
        │               ├── questions/
        │               ├── runners/
        │               ├── stepdefinitions/
        │               ├── tasks/
        │               ├── ui/
        │               └── util/
        └── resources/
            └── features/
                └── registro.feature
```

---

## Descripción de Cada Elemento

### Archivos Raíz

#### `build.gradle`
Archivo de configuración de Gradle. Define las dependencias del proyecto (Serenity BDD,
Cucumber, Selenium, etc.), los plugins necesarios y las tareas de construcción y ejecución
de las pruebas.

#### `settings.gradle`
Define el nombre del proyecto Gradle. Es el punto de entrada que Gradle usa para identificar
el proyecto raíz y sus subproyectos (si los hubiera).

#### `serenity.conf`
Archivo de configuración principal de Serenity BDD escrito en formato HOCON. Aquí se
configuran aspectos como el navegador a utilizar, la URL base de la aplicación, el driver
de Selenium/WebDriver y opciones de generación de reportes.

#### `serenity.properties`
Archivo complementario de configuración de Serenity BDD en formato clave=valor. Permite
ajustar propiedades como el nombre del proyecto en los reportes, el directorio de salida
y los títulos que aparecerán en la pantalla de resultados HTML.

---

### `src/test/resources/features/`

Contiene los archivos `.feature` escritos en lenguaje **Gherkin**. Estos archivos describen
el comportamiento esperado del sistema en lenguaje natural, siguiendo la sintaxis
`Feature / Scenario / Given / When / Then`.

#### `registro.feature`
Define los escenarios de prueba para el flujo de reporte de amenazas en CyberGuard System.
Contiene dos escenarios independientes:
- **Flujo positivo:** Reporte exitoso de una amenaza con datos válidos.
- **Flujo negativo:** Intento de reporte fallido por datos incompletos.

> Los escenarios están redactados de forma **declarativa**, enfocados en el comportamiento
> del negocio, sin exponer detalles técnicos de implementación.

---

### `src/test/java/com/cyberguard/automation/`

Contiene todo el código fuente Java de la automatización, organizado por responsabilidad.

---

#### `hooks/`
Contiene las clases de **configuración del ciclo de vida** de los escenarios de Cucumber.

- **Responsabilidad:** Inicializar y destruir el actor (y el navegador) antes y después
  de cada escenario.
- **Clase típica:** `CucumberHooks.java`
- **Anotaciones clave:** `@Before` y `@After` de Cucumber.

```
hooks/
└── CucumberHooks.java   ← Configura el Actor con habilidades (BrowseTheWeb)
                            y limpia los recursos al finalizar cada escenario.
```

---

#### `questions/`
Contiene las **preguntas** que el actor hace al sistema para verificar su estado.

- **Responsabilidad:** Consultar el estado actual de la interfaz (texto visible,
  presencia de elementos, mensajes de error) y retornar un valor que pueda ser
  validado mediante `Actor.should(seeThat(...))`.
- **Clases típicas:** `SuccessMessage.java`, `ValidationMessage.java`

```
questions/
├── SuccessMessage.java     ← Pregunta: ¿Está visible el mensaje de éxito?
└── ValidationMessage.java  ← Pregunta: ¿Qué mensaje de validación se muestra?
```

> Las preguntas implementan la interfaz `Question<T>` de Serenity, donde `T` es
> el tipo de dato que retornan (normalmente `String` o `Boolean`).

---

#### `runners/`
Contiene la clase **runner de Cucumber** que orquesta la ejecución de las pruebas.

- **Responsabilidad:** Configurar Cucumber indicando la ruta de los features, el
  paquete de los step definitions, los plugins de reporte (Serenity) y las opciones
  de filtrado por etiquetas (`@tags`).
- **Clase típica:** `CyberGuardTestRunner.java`

```
runners/
└── CyberGuardTestRunner.java ← Punto de entrada de las pruebas. Usa @RunWith y
                                 @CucumberOptions para orquestar la ejecución.
```
---


#### `stepdefinitions/`
Contiene las clases que **conectan los pasos Gherkin con el código Java**.

- **Responsabilidad:** Traducir cada paso `Given / When / Then` del archivo `.feature`
  en una llamada a una tarea o pregunta del patrón Screenplay. No contienen lógica
  de negocio ni de UI directamente.
- **Clase típica:** `ThreatReportStepDefinitions.java`

```
stepdefinitions/
└── ThreatReportStepDefinitions.java ← Mapea los pasos Gherkin a Tasks y Questions
                                        mediante anotaciones @Given, @When, @Then.
```

---

#### `tasks/`
Contiene las **tareas** que un actor puede realizar en la interfaz.

- **Responsabilidad:** Encapsular una acción de negocio compuesta por una o más
  interacciones atómicas (clicks, escritura, esperas). Cada clase representa
  **una única responsabilidad** (principio SRP).
- **Clases típicas:** `Authenticate.java`, `ReportThreat.java`, `SubmitForm.java`

```
tasks/
├── Authenticate.java    ← Tarea: el actor inicia sesión en el sistema.
├── ReportThreat.java    ← Tarea: el actor completa y envía el formulario de amenaza.
└── SubmitEmptyForm.java ← Tarea: el actor intenta enviar el formulario sin datos.
```

> Cada tarea implementa la interfaz `Task` de Serenity e implementa el método
> `performAs(Actor actor)`, que es donde se definen las interacciones con la UI.

---

#### `ui/`
Contiene las clases de **localizadores de elementos** de la interfaz de usuario.

- **Responsabilidad:** Mapear los elementos del DOM (botones, inputs, labels) usando
  la clase `Target` de Serenity Screenplay, que asocia un nombre legible con un
  selector CSS o XPath.
- **Clase típica:** `ThreatReportPage.java`

```
ui/
└── ThreatReportPage.java   ← Define los Target (localizadores) de los elementos
                               visibles en la pantalla de reporte de amenazas.
```

> **Diferencia clave con POM:** En POM, la clase de página contiene tanto localizadores
> como acciones. En Screenplay, la capa `ui/` **solo define localizadores**; las acciones
> pertenecen a las `tasks/`.

---

#### `util/`
Contiene clases **utilitarias y de soporte transversal** al proyecto.

- **Responsabilidad:** Proveer constantes, datos de prueba, helpers de espera o
  cualquier funcionalidad reutilizable que no pertenezca a ninguna de las otras
  capas.
- **Clases típicas:** `TestData.java`, `WaitHelper.java`

```
util/
├── TestData.java   ← Constantes y datos de prueba (URLs, credenciales, mensajes).
└── WaitHelper.java ← Métodos de espera explícita reutilizables entre tasks.
```

---

## Flujo de Ejecución

El siguiente diagrama muestra cómo interactúan las capas durante la ejecución de un escenario:

```
.feature (Gherkin)
    │
    ▼
StepDefinitions       ← Traduce pasos Gherkin a código Java
    │
    ├──▶ Tasks         ← El Actor ejecuta acciones sobre la UI
    │       │
    │       └──▶ UI (Target) ← Localizadores de elementos del DOM
    │
    └──▶ Questions     ← El Actor consulta el estado de la UI
            │
            └──▶ Assertions (Serenity) ← Valida el resultado esperado
```

---

## Relación entre Capas y Principios SOLID

| Capa | Principio SOLID Aplicado | Justificación |
|---|---|---|
| `tasks/` | **S** - Single Responsibility | Cada tarea hace una sola cosa |
| `questions/` | **S** - Single Responsibility | Cada pregunta consulta un solo estado |
| `ui/` | **O** - Open/Closed | Agregar localizadores sin modificar tasks |
| `stepdefinitions/` | **D** - Dependency Inversion | Depende de abstracciones (Task, Question) |
| `hooks/` | **S** - Single Responsibility | Solo gestiona el ciclo de vida del actor |