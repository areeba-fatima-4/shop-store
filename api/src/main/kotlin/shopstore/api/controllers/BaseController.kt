package shopstore.api.controllers

import org.springframework.http.ResponseEntity
import shopstore.api.dtos.ResponseDto

class BaseController {
    companion object {
        fun <T> response(response : ResponseDto<T>) =
            ResponseEntity(response, response.status);
    }
}