# Money Calculator

Esta aplicación permite realizar conversiones de moneda utilizando una API externa, con capacidades añadidas de modo offline para garantizar su funcionamiento en entornos sin conexión.

## 1. Implementación de Modo Offline
Se ha añadido la clase `FileStorage` para gestionar el almacenamiento de datos en archivos JSON, permitiendo que la aplicación se proteja contra fallos de red:

* **Caché de Monedas:** La lista de divisas se almacena en `currencies.json`. Esto evita que los selectores de la interfaz aparezcan vacíos si no hay conexión al iniciar la aplicación.
* **Historial de Tasas:** Las tasas de cambio consultadas se guardan en `rates_cache.json`. Si la solicitud a la API falla, el cargador recupera automáticamente la última tasa almacenada para ese par de divisas. En caso de que no lo encuentra, lanza una excepción.

## 2. Rediseño de la Interfaz Gráfica (Swing)
Se ha actualizado la clase `Desktop` para mejorar la usabilidad y estética de la herramienta:

* **Distribución (Layout):** Uso de `BoxLayout` y márgenes internos (`EmptyBorder`) para organizar los componentes de forma más limpia y profesional.
* **Estilo de Componentes:** Mejora en la tipografía y el uso de colores de acento en el botón de acción para facilitar la interacción.
* **Formato de Salida:** El campo de resultado ahora es de solo lectura y formatea el valor numérico a dos decimales, siguiendo el estándar de representación monetaria.
