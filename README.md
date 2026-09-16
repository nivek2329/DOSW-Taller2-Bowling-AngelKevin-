# \# Taller 2 - Bowling Scoring Engine (TDD)

# 

# \## 1. Identificación

# 

# \- \*\*Estudiante:\*\* Angel Kevin

# \- \*\*Usuario de GitHub:\*\* nivek2329

# \- \*\*Asignatura:\*\* Diseño y Arquitectura de Software (DOSW) - Periodo 2026-2

# \- \*\*Institución:\*\* Escuela Colombiana de Ingeniería Julio Garavito

# \- \*\*Repositorio:\*\* https://github.com/nivek2329/Bitacora-Dosw-Corte2\_KevinAngel

# \- \*\*Rama de trabajo:\*\* `feature/AngelKevin\_bowling`

# 

# \## 2. Descripción del proyecto

# 

# Este proyecto implementa un motor de calculo de puntaje para el juego de Bowling (10 pines), desarrollado en Java 24 siguiendo estrictamente la metodologia de Test-Driven Development (TDD) mediante ciclos RED-GREEN-REFACTOR.

# 

# El sistema soporta las reglas clasicas del bowling: acumulacion normal de pinos por frame, bonos por \*spare\* (suma del siguiente tiro), bonos por \*strike\* (suma de los dos tiros siguientes) y el manejo especial del decimo frame (hasta 3 tiros con reglas de bono).

# 

# \### Arquitectura

# 

# El diseño se compone de 4 clases principales:

# 

# \- \*\*`FrameType`\*\*: enum que representa el estado de un frame (`NORMAL`, `SPARE`, `STRIKE`, `TENTH`).

# \- \*\*`Frame`\*\*: representa un frame individual, almacena los tiros registrados y determina si esta completo (`isFull()`).

# \- \*\*`BowlingGame`\*\*: orquesta la partida completa; valida los tiros, gestiona la progresion de frames, detecta strikes/spares y expone `roll()`, `score()` e `isComplete()`.

# \- \*\*`BowlingScorer`\*\*: implementa el algoritmo clasico de calculo de puntaje (aplanado de tiros con "look-ahead" para bonos de strike/spare).

# 

# \## 3. Evidencia TDD

# 

# El desarrollo se realizo en ciclos estrictos \*\*RED -> GREEN -> REFACTOR\*\*, con un commit de Git independiente por cada fase, usando los prefijos:

# 

# \- `test: RED - ...` (prueba que falla)

# \- `feat: GREEN - ...` (implementacion minima que la hace pasar)

# \- `refactor: ...` (mejora del diseño sin cambiar el comportamiento)

# 

# \### Casos de prueba implementados (22 en total)

# 

# | Modulo | Archivo de test | Casos | Descripcion |

# |---|---|---|---|

# | A | `BowlingGameTest.java` | A1 - A8 | Validacion de rango de pinos, progresion de frames, deteccion de strike/spare, suma maxima por frame, excepcion al jugar sobre juego completo |

# | B | `BowlingScorerTest.java` | B1 - B8 | Calculo de puntaje: sin bonos, ceros, spare, strike, strikes consecutivos, todos spares, juego perfecto (300), excepcion si el juego no esta completo |

# | C | `BowlingGameTest.java` | C1 - C6 | Deteccion de finalizacion del juego (`isComplete()`) en distintos escenarios del decimo frame |

# 

# Todos los tests pasan (`Tests run: 22, Failures: 0`, `BUILD SUCCESS`).

# 

# \### Ciclo documentado como ejemplo (caso B2 - spare con bono)

# 

# \- \*\*RED:\*\* `docs/evidence/B2-red.png`, `docs/evidence/B2-red-2.png` (`expected: <70> but was: <0>`)

# \- \*\*GREEN:\*\* `docs/evidence/B2-green.png`, `docs/evidence/B2-green-2.png` (`Tests run: 10, Failures: 0`)

# 

# \### Ejemplo de REFACTOR documentado

# 

# Tras el analisis de SonarQube se refactorizo el metodo `roll()` de `BowlingGame`, dividiendolo en metodos auxiliares privados (`ensureCurrentFrameExists`, `validateSecondRoll`, `updateFrameType`) para reducir su complejidad cognitiva de 18 a menos de 15, se elimino un campo no utilizado (`currentFrame`) y se reemplazo una expresion lambda por una referencia a metodo (`game::score`) en las pruebas. Los 22 tests se mantuvieron en verde durante todo el refactor.

# 

# \## 4. Cobertura de codigo (JaCoCo)

# 

# Se configuro `jacoco-maven-plugin` en `pom.xml` con una regla de verificacion (`check`) que exige minimo \*\*85% de cobertura de lineas\*\* y \*\*70% de cobertura de ramas\*\*, vinculada a la fase `verify` de Maven.

# 

# \- \*\*Antes\*\* (`docs/evidence/jacoco-antes.png`): al deshabilitar temporalmente las pruebas de `BowlingScorer`, la cobertura cae a \~70% de lineas / \~77% de ramas, y `mvn clean verify` falla con `BUILD FAILURE` (`Rule violated for bundle bowling-tdd: lines covered ratio is 0.70, but expected minimum is 0.85`).

# \- \*\*Final\*\* (`docs/evidence/jacoco-final.png`): con la suite completa de 22 tests, la cobertura alcanza \*\*99.4% de lineas\*\* y cobertura alta de ramas, cumpliendo ampliamente el umbral exigido, con `BUILD SUCCESS`.

# 

# \## 5. Analisis estatico (SonarQube)

# 

Se levanto una instancia local de SonarQube Community (`sonarqube:26.9.0.129388-community`) mediante Docker Desktop, y se creo el proyecto `bowling-tdd`, analizado con `sonar-maven-plugin` ejecutando:
mvn clean verify sonar:sonar -Dsonar.token=%SONAR\_TOKEN%


===

# El token de analisis se genero desde la interfaz de SonarQube y se paso unicamente como variable de entorno (`SONAR\_TOKEN`), nunca almacenado en archivos del repositorio.

# 

# \### Resultado inicial

# 

# `docs/evidence/sonarqube-inicial.png` — Quality Gate: \*\*Passed\*\*, pero con \*\*3 issues abiertos de Maintainability\*\* (Code Smells):

# 

# 1\. \*\*Alta severidad:\*\* complejidad cognitiva del metodo `roll()` de 18, superando el limite permitido de 15.

# 2\. \*\*Media severidad:\*\* campo privado `currentFrame` declarado pero nunca utilizado.

# 3\. \*\*Baja severidad:\*\* uso de una expresion lambda (`() -> game.score()`) donde correspondia una referencia a metodo (`game::score`).

# 

# \### Acciones correctivas

# 

# Se aplico un ciclo de REFACTOR (ver seccion 3) que dividio el metodo `roll()` en metodos auxiliares mas pequeños, elimino el campo sin uso y sustituyo la lambda por una referencia a metodo, sin alterar el comportamiento (22 tests siguieron en verde).

# 

# \### Resultado final

# 

# `docs/evidence/sonarqube-final.png` — Quality Gate: \*\*Passed\*\*, \*\*0 issues abiertos\*\* en Security, Reliability y Maintainability, \*\*0.0% de duplicaciones\*\*, \*\*99.4% de cobertura\*\*.

# 

# \## 6. Pull Requests

# 

# \- El desarrollo se realizo en la rama `feature/AngelKevin\_bowling`, creada a partir de `develop`.

# \- Los cambios llegan a `develop` unicamente a traves de Pull Request (nunca mediante push directo).

# \- Se agrego como colaborador del repositorio al profesor `MartinRapyd990558`, y se envio la notificacion por correo a `andres.cantor-u@escuelaing.edu.co` con el asunto `\[DOSW] Taller 2 - Angel Kevin`.

# \- \*\*Pull Request:\*\* \_(pendiente de agregar el enlace una vez creado)\_

# 

# \## 7. Reflexion

# 

# \_(pendiente)\_

