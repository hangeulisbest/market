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


    @Transactional
    public Long save(UserSaveRequestDto userSaveRequestDto){
        return userRepository.save(userSaveRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public UserListResponseDto findAll(){
        List<User> all = userRepository.findAll();
        List<UserResponseDto> collect =
                        all
                        .stream()
                        .map(o -> new UserResponseDto(o.getId()
                                ,o.getName()
                                ,o.getAddress().getCity()
                                ,o.getAddress().getStreet()
                                ,o.getAddress().getZipcode())).collect(Collectors.toList());
        return UserListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });
        return new UserResponseDto(
                user.getId()
                ,user.getName()
                ,user.getAddress().getCity()
                ,user.getAddress().getStreet()
                ,user.getAddress().getZipcode());
    }


    @Transactional
    public Long updateAddress(Long id, UserUpdateRequestDto requestDto){
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });
        user.updateAddress(requestDto.getCity(),requestDto.getStreet(),requestDto.getZipcode());
        return id;
    }

    @Transactional
    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        });
        userRepository.delete(user);
    }
}
