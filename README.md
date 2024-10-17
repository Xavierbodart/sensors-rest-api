# API Documentation

## Overview

This API allows users to manage signals and associated keywords in the system. The following endpoints are available:

### 1. Get Signal by Node ID

**GET** `/signals/{nodeId}`

Retrieves the signal details for the specified node ID.

**Parameters:**

- `nodeId` (path parameter): The unique identifier for the signal.

**Responses:**

- **200 OK**: Returns the signal details.
- **404 Not Found**: If no signal exists for the provided `nodeId`.

---

### 2. Update Signal by Node ID

**PUT** `/signals/{nodeId}`

Updates the signal information for the specified node ID.
Only `deadbandType`, `deadbandValue` and `samplingInterval` fields will be updated.

**Parameters:**

- `nodeId` (path parameter): The unique identifier for the signal.

**Request Body:**

- A JSON object containing the updated signal details.

**Responses:**

- **200 OK**: Returns the updated signal details.
- **404 Not Found**: If no signal exists for the provided `nodeId`.
- **400 Bad Request**: If the request body is invalid.

---

### 3. Load Triggers

**POST** `/signals/load-triggers`

Loads trigger data from into the system. 
It will read the file from the path defined with `signal.file.path` application property and load data in `SIGNAL_MONITORING` database.

**Responses:**

- **201 Created**: Successfully loads triggers.
- **400 Bad Request**: If the request body is invalid.

---

### 4. Get All Signals

**GET** `/signals`

Retrieves a list of all signals in the system.

**Responses:**

- **200 OK**: Returns an array of signals.

---

### 5. Get Keywords for a Signal

**GET** `/signals/{nodeId}/keywords`

Retrieves the keywords associated with the specified signal.

**Parameters:**

- `nodeId` (path parameter): The unique identifier for the signal.

**Responses:**

- **200 OK**: Returns an array of keywords associated with the signal.
- **404 Not Found**: If no signal exists for the provided `nodeId`.

---

## Example Requests

**See OpenAPI documentation:** http://localhost:8080/swagger-ui/index.html#/signals-controller/searchSignals
