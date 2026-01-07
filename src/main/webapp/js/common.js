function fetchWithAuth(url, options = {}) {

    const defaultHeaders = {
        "X-CSRF-TOKEN": csrfToken
    };

    if (options.body && !options.headers?.["Content-Type"]) {
        defaultHeaders["Content-Type"] = "application/json";
    }

    options.headers = {
        ...defaultHeaders,
        ...(options.headers || {})
    };

    return fetch(url, options);
}
/**
 * 
 */