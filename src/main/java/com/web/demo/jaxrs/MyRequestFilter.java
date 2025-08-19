package com.web.demo.jaxrs;

/*
ContainerRequestFilter
ContainerResponseFilter
ContainerRequestContext requestContext,ContainerResponseContext responseContext
ClientRequestContext
ClientResponseContext

| Use Case                    | JAX-RS Filter              | Spring Boot Equivalent                        |
| --------------------------- | -------------------------- | --------------------------------------------- |
| Server-side request filter  | `ContainerRequestContext`  | `OncePerRequestFilter` / `HandlerInterceptor` |
| Server-side response filter | `ContainerResponseContext` | `OncePerRequestFilter` / `ResponseBodyAdvice` |
| Client-side request filter  | `ClientRequestContext`     | `RestTemplate` or `WebClient` interceptor     |
| Client-side response filter | `ClientResponseContext`    | `RestTemplate` or `WebClient` interceptor     |

 */
/*@Provider
public class MyRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        URI uri = requestContext.getUriInfo().getRequestUri();
        System.out.println("Incoming request: " + method + " " + uri);

        // Example: block request if unauthorized
        if (!requestContext.getHeaders().containsKey("Authorization")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}*/
