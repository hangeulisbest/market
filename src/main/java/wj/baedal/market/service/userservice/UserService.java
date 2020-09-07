package wj.baedal.market.service.userservice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.user.UserListResponseDto;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.controller.dto.user.UserSaveRequestDto;
import wj.baedal.market.controller.dto.user.UserUpdateRequestDto;
import wj.baedal.market.entity.user.User;
import wj.baedal.market.entity.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    /**
     *  유저 정보 저장
     * */
    @Transactional
    public Long save(UserSaveRequestDto userSaveRequestDto){
        return userRepository.save(userSaveRequestDto.toEntity()).getId();
    }

    /**
     *  모든 유저 조회
     * */
    @Transactional(readOnly = true)
    public UserListResponseDto findAll(){
        /** 모든 유저를 조회*/
        List<User> all = userRepository.findAll();

        /** List<User> -> List<UserResponseDto> 로 변경 */
        List<UserResponseDto> collect =
                        all
                        .stream()
                        .map(o -> new UserResponseDto(o.getId()
                                ,o.getName()
                                ,o.getAddress().getCity()
                                ,o.getAddress().getStreet()
                                ,o.getAddress().getZipcode())).collect(Collectors.toList());

        /** List<UserResponseDto> -> UserListResponseDto 로 변경 */
        return UserListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();
    }


    /**
     *  id 로 유저정보 조회
     * */
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id){
        /**
         *  아이디가 존재하지 않으면 에러를 반환한다.
         * */
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });

        /** User -> UserResponseDto 로 변경 */
        return new UserResponseDto(
                user.getId()
                ,user.getName()
                ,user.getAddress().getCity()
                ,user.getAddress().getStreet()
                ,user.getAddress().getZipcode());
    }


    /**
     *  유저의 주소를 수정한다.
     * */
    @Transactional
    public Long updateAddress(Long id, UserUpdateRequestDto requestDto){
        /**
         *  아이디가 존재하지 않으면 에러를 반환한다.
         * */
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });

        /** 유저 객체의 메소드로 유저정보를 더티체킹으로 수정 */
        user.updateAddress(requestDto.getCity(),requestDto.getStreet(),requestDto.getZipcode());
        return id;
    }

    /**
     *  유저 삭제
     * */
    @Transactional
    public void delete(Long id){
        /**
         *  아이디가 존재하지 않으면 에러를 반환한다.
         * */
        User user = userRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });
        userRepository.delete(user);
    }
}
