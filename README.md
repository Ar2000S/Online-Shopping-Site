# Documentation-Le Shopping Site

A full-stack online shopping web application built with **Spring Boot** (backend) and **Vue.js** (two separate frontends: customer and administrator).

---

## Project Structure

```
online_shopping_backend/          Spring Boot backend (REST API + serves both frontends)
online_shopping_frontend/
    ├── user/                  Customer-facing Vue app
    └── admin/                 Administrator Vue app
```

The two frontends are built and bundled into the backend's static resources, so a single running backend serves everything.

---

## Requirements

- **Java 17** (subject to change)
- **Maven** (or the bundled Maven wrapper / IDE Maven)
- **Node.js** (only needed if rebuilding the frontends)
- **MariaDB** access via the provided tunnel (schema: `test`)

---

## Database Setup

The application connects to the **`test`** schema, which already contains the six required tables
(`ONLINE_MEMBER`, `ONLINE_STAFF`, `ONLINE_CATEGORY`, `ONLINE_PRODUCT`, `ONLINE_ORDER`, `ONLINE_ORDER_LIST`).

Connection details are read from environment variables (with fallback placeholders in
`application.properties`):

| Variable | Purpose |
|----------|---------|
| `SPRING_DATASOURCE_URL` | e.g. `jdbc:mariadb://localhost:13306/test` |
| `SPRING_DATASOURCE_USERNAME` | database username |
| `SPRING_DATASOURCE_PASSWORD` | database password |

Set these in your IDE run configuration or environment before starting the backend.
Make sure the database tunnel is connected first.

---

## Running the Application (bundled run is recommended)

The frontends are already built into `FinalProject_backend/src/main/resources/static/`,
so just start the backend, and you should be able to access.

1. Ensure the database tunnel is connected and the datasource environment variables are set.
2. Start the backend: Running using IDE is recommended
3. Access the urls.

### Access URLs

| Application | URL |
|-------------|-----|
| **Customer site** | http://localhost:8080/ |
| **Administrator site** | http://localhost:8080/admin/ |

---

## How to Use

### Customer Site (`http://localhost:8080/`)
- **Register** a new member (New Member Registration), or **log in** with an existing member number + password.
- Browse and search products, view product details, add items to the cart.
- Review the cart, confirm the order, and place it.
- View, edit, or delete your own member information.

### Admin Site (`http://localhost:8080/admin/`)
- Log in with a **staff number + password** (from the `ONLINE_STAFF` table).
- Search purchase history by member, date range, or total amount.
- View purchase history details.
- Delete purchase history records.

> **Note:** Passwords are stored as plain text in the provided training schema
> (the `PASSWORD` column is `varchar(8)`), so log in using the exact stored values.

---

## Development Mode (optional — rebuilding frontends)

Each runs on its own dev server with an API proxy to the backend.

### Customer frontend
```
cd FinalProject_frontend/user
npm install
npm run dev          # runs on http://localhost:5173
```

### Administrator frontend
```
cd FinalProject_frontend/admin
npm install
npm run dev          # runs on http://localhost:5174
```

Both dev servers proxy `/api` requests to the backend at `http://localhost:8080`, so the backend must
also be running.

### Rebuilding & re-bundling into the backend
After changing a frontend, rebuild and copy the output into the backend's static folder:

```
# build customer app
cd online_shopping_frontend/user
npm run build

# build admin app
cd ../admin
npm run build
```

Then copy the builds into the backend:
- `user/dist/*`  → `online_shopping_backend/src/main/resources/static/`
- `admin/dist/*` → `online_shopping_backend/src/main/resources/static/admin/`

Rebuild the backend so the updated static files are served.

---

## Notes

- The customer and administrator sites are **separate applications** served from the same backend
  (customer at `/`, admin at `/admin/`).
- Client-side routing is handled so that reloading any route (e.g. `/member-info` or `/admin/history`)
  serves the correct application.
- Screen validation, system messages (MSG001–MSG014), and business rules follow the provided
  design documents.
