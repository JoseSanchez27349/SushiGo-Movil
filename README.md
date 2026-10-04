
# Plataforma E-Commerce — Aplicación Móvil (Clientes y Repartidores)

Este repositorio contiene la **Aplicación Móvil** multiplataforma orientada a los perfiles de **Cliente** (para consulta de catálogo y realización de compras) y **Repartidor** (para gestión y confirmación de entregas a domicilio).

---

## Información del Proyecto
- **Integrantes del Equipo:**
  - José Armando Abarca Sánchez
  - Isaac Alejandro Padrón Becerra
  - Jorge Alexis Ramos Equihua
  - Leticia Guadalupe

---

## Tecnologías Utilizadas
- **Framework Móvil:** React Native / Flutter (Cross-platform Android e iOS)
- **Lenguaje:** JavaScript / TypeScript / Dart
- **Gestión de Estado:** Context API / Redux / Provider
- **Conexión de Red:** Axios / Fetch API con soporte para resiliencia ante pérdida de señal

---

##  Perfiles de Usuario y Requerimientos Cubiertos

### 1. Perfil: Cliente[cite: 6]
Usuario que consulta productos, gestiona su carrito y realiza pedidos a domicilio.

#### Requerimientos Funcionales (RF-CLI):
- **RF-CLI-01 (Registro de Cuenta):** Alta de usuario con nombre, correo, teléfono y contraseña.
- **RF-CLI-02 (Inicio y Cierre de Sesión):** Autenticación segura en la app.
- **RF-CLI-03 (Consulta del Catálogo):** Visualización de catálogo con imagen, nombre, precio y categoría.
- **RF-CLI-04 (Búsqueda y Filtrado):** Búsqueda por texto y filtros por categorías.
- **RF-CLI-05 (Consulta de Precio y Stock):** Vista en tiempo real de fichas de producto y disponibilidad.
- **RF-CLI-06 (Gestión del Carrito):** Agregar, modificar cantidades o eliminar artículos.
- **RF-CLI-07 (Datos de Entrega):** Registro/selección de dirección y contacto para envío.
- **RF-CLI-08 (Confirmación de Pedido):** Procesamiento de compras con cálculo del costo total.
- **RF-CLI-09 (Seguimiento del Estado):** Rastreo en tiempo real de la orden (Recibido, En preparación, En camino, Entregado).
- **RF-CLI-10 (Historial de Compras):** Consulta de registros de compras anteriores.

#### Requerimientos No Funcionales (RNF-CLI):
- **RNF-CLI-01:** Proceso de compra completable en un máximo de 4 pasos.
- **RNF-CLI-02:** Sincronización del estado del pedido en menos de 3 segundos.
- **RNF-CLI-03:** Compatibilidad multiplataforma (Android e iOS).
- **RNF-CLI-04:** Protección y privacidad estricta de direcciones y teléfonos.

---

### 2. Perfil: Repartidor
Usuario encargado de realizar las entregas de los pedidos en campo[cite: 7].

#### Requerimientos Funcionales (RF-REP)[cite: 9]:
- **RF-REP-01 (Autenticación):** Inicio de sesión con credenciales asignadas[cite: 9].
- **RF-REP-02 (Consulta de Asignaciones):** Listado exclusivo de pedidos asignados por el Administrador[cite: 9].
- **RF-REP-03 (Consulta de Datos de Entrega):** Vista operativa con nombre de cliente, dirección exacta, teléfono y detalle[cite: 9].
- **RF-REP-04 (Actualización de Estado):** Cambio de fase del envío (ej. "En camino")[cite: 9].
- **RF-REP-05 (Confirmación de Entrega):** Validación y marcaje del pedido como "Entregado"[cite: 9].

#### Requerimientos No Funcionales (RNF-REP)[cite: 9]:
- **RNF-REP-01:** Interfaz con botones amplios e intuitivos para uso ágil en campo[cite: 9].
- **RNF-REP-02:** Consumo optimizado de datos móviles y batería[cite: 9].
- **RNF-REP-03:** Resiliencia y almacenamiento en caché para operar con intermitencia de red[cite: 9].

---

## Identificación del Backend y API Centralizada

La app móvil conecta directamente con el servidor API Backend[cite: 5, 10]:

- **Tipo de API:** RESTful API sobre HTTPS[cite: 5, 10]
- **Módulo de conexión:** `src/services/apiService.js`
- **URL Base:** `http://localhost:3000/api` (o IP de red local para emuladores: `http://10.0.2.2:3000/api`)
- **Endpoints consumidos por la App Móvil:**
  - `POST /api/auth/register` — Registro de cliente[cite: 8].
  - `POST /api/auth/login` — Autenticación (Cliente/Repartidor)[cite: 8, 9].
  - `GET /api/productos` — Consulta de catálogo y filtros[cite: 8].
  - `POST /api/pedidos` — Creación y confirmación de órdenes de compra[cite: 8].
  - `GET /api/pedidos/cliente` — Historial de pedidos del cliente[cite: 9].
  - `GET /api/repartidor/pedidos` — Consulta de pedidos asignados al repartidor[cite: 9].
  - `PUT /api/pedidos/:id/estado` — Actualización del flujo de entrega por el repartidor[cite: 9].

---

## Estructura del Proyecto

```text
proyecto-movil/
├── assets/                 # Iconos, imágenes de catálogo y recursos visuales
├── src/
│   ├── components/         # Botones, Cards de productos, modales
│   ├── screens/
│   │   ├── client/         # Pantallas: Catálogo, Carrito, Historial, Rastreo
│   │   └── driver/         # Pantallas: Pedidos Asignados, Detalle de Entrega
│   ├── services/           # apiService.js para peticiones HTTP al Backend
│   ├── context/            # Manejo global de autenticación y carrito
│   └── config/             # Configuración de IP y variables de entorno
├── App.js
├── package.json
└── README.md
