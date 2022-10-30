package shopstore.api.services

import org.springframework.stereotype.Service
import shopstore.api.domain.UserEntity
import shopstore.api.dtos.UserDto
import shopstore.api.models.User
import shopstore.api.repositories.UserRepository
import javax.transaction.Transactional

interface UserDtoService: BaseService<UserDto, User> {
}

@Service
@Transactional
class UserDtoServiceImpl(
    inner: UserServiceImpl
) : BaseDtoServiceImpl<UserDto, User, UserEntity, UserServiceImpl, UserRepository>(inner) {

}

@Service
@Transactional
class UserServiceImpl(
    repository: UserRepository
) : BaseServiceImpl<UserDto, User, UserEntity, UserRepository>(repository){

}