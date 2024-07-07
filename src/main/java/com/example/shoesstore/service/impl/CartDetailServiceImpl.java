package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.ShoeDetailStatus;
import com.example.shoesstore.dto.request.CartDetailRequest;
import com.example.shoesstore.dto.response.CartDetailResponse;
import com.example.shoesstore.dto.response.CartResponse;
import com.example.shoesstore.dto.response.UserResponse;
import com.example.shoesstore.entity.Cart;
import com.example.shoesstore.entity.CartDetail;
import com.example.shoesstore.entity.ShoeDetail;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.CartDetailMapper;
import com.example.shoesstore.mapper.CartMapper;
import com.example.shoesstore.repository.CartDetailRepository;
import com.example.shoesstore.repository.CartRepository;
import com.example.shoesstore.repository.ShoeDetailRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.CartDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CartDetailServiceImpl implements CartDetailService {
    CartDetailRepository cartDetailRepository;
    CartRepository cartRepository;
    ShoeDetailRepository shoeDetailRepository;
    CartDetailMapper cartDetailMapper;
    CartMapper cartMapper;
    UserRepository userRepository;

    ModelMapper modelMapper;

    @Override
    public CartResponse addProductToCartDetail(CartDetailRequest request, Long idUser) {
        log.info("idUser {}", idUser);
        User user = userRepository.findById(idUser).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Cart cart = user.getCart();
        log.info("idCart {}", cart.getId());

        List<CartDetail> listCartDetail = cart.getCartDetails();

        ShoeDetail shoeDetail = shoeDetailRepository.findById(request.getIdShoeDetail()).orElseThrow(() -> new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND));

        if (shoeDetail.getStatus().equals(ShoeDetailStatus.NO_ACTIVE) || shoeDetail.getQuantity() == 0) {
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        // set product to cart detail
        CartDetail cartDetail = new CartDetail();
        cartDetail.setShoeDetail(shoeDetail);
        cartDetail.setQuantity(1);
        cartDetail.setPrice(shoeDetail.getPrice());
        cartDetail.setCart(cart);

        boolean isFound = false;

        // check sp đã có trong giỏ -> update cart detail
        for (CartDetail x : listCartDetail) {
            Integer quantityUpdate = x.getQuantity();
            if (x.getShoeDetail().getId() == request.getIdShoeDetail()) {
                isFound = true;
                quantityUpdate++;
                x.setQuantity(quantityUpdate);
                x.setPrice(quantityUpdate * shoeDetail.getPrice());
                log.info("SP found");
                cartDetailRepository.save(x);
                break;
            }
        }

        // check nếu không có trong giỏ -> add to cart detail
        if (!isFound) {
            listCartDetail.add(cartDetail);
            cart.setTotalPrice(cart.getTotalPrice() + cartDetail.getShoeDetail().getPrice());
            log.info("Chua co trong cart ");
            cartDetailRepository.save(cartDetail);
        }

        Long totalPrice = totalPrice(listCartDetail);

        cart.setCartDetails(listCartDetail);
        cart.setTotalPrice(totalPrice);
        cart.setUsers(user);

        CartResponse cartResponse = cartMapper.mapToResponse(cartRepository.save(cart));
        cartResponse.setListCartDetail(getListCartDetail(listCartDetail));

        return cartResponse;
//        return modelMapper.map(cartRepository.save(cart), CartResponse.class);
    }

    public static Long totalPrice(List<CartDetail> list) {
        Long totalPrice = list.stream()
                .map(x -> x.getPrice())
                .reduce(0L, (x, y) -> x + y);
        return totalPrice;
    }

    public List<CartDetailResponse> getListCartDetail(List<CartDetail> listCartDetail) {
        return listCartDetail.stream()
                .map(cartDetail1 -> cartDetailMapper.mapToResponse(cartDetail1))
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse updateCartDetail(Long idUser, Long idCartDetail, CartDetailRequest request) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        CartDetail cartDetail = cartDetailRepository.findById(idCartDetail)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        ShoeDetail shoeDetail = shoeDetailRepository.findById(request.getIdShoeDetail())
                .orElseThrow(() -> new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND));

        if (cartDetail.getShoeDetail() != shoeDetail) {
            throw new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND);
        }

        // set lại cart detail
        cartDetail.setShoeDetail(shoeDetail);
        cartDetail.setQuantity(cartDetail.getQuantity() + request.getQuantity());
        cartDetail.setPrice(request.getQuantity() * shoeDetail.getPrice());
        cartDetailRepository.save(cartDetail);

        // set lại cart
        Cart cart = cartRepository.findById(request.getIdCart()).get();
        List<CartDetail> listCartDetail = cart.getCartDetails();
        Long totalPrice = this.totalPrice(listCartDetail);

        cart.setCartDetails(listCartDetail);
        cart.setTotalPrice(totalPrice);

        CartResponse cartResponse = cartMapper.mapToResponse(cartRepository.save(cart));
        cartResponse.setListCartDetail(getListCartDetail(listCartDetail));

        return cartResponse;
//        return modelMapper.map(cartRepository.save(cart), CartResponse.class);
//                mapToResponse(cartRepository.save(cart));
    }


//    private CartResponse mapToResponse(Cart cart) {
//        CartResponse cartResponse = new CartResponse();
//        cartResponse.setId(cart.getId());
//        cartResponse.setUser(mapToUserResponse(cart.getUsers()));
//        cartResponse.setCartDetails(cart.getCartDetails().stream().map(this::mapToResponseCartDetail).collect(Collectors.toList()));
//        cartResponse.setTotalPrice(cart.getTotalPrice());
//        return cartResponse;
//    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setStatus(user.getStatus());
        userResponse.setGender(user.getGender());
        userResponse.setAddress(user.getAddress());
        return userResponse;
    }

//    private CartDetailResponse mapToResponseCartDetail(CartDetail cartDetail) {
//        CartDetailResponse cartDetailResponse = new CartDetailResponse();
//        cartDetailResponse.setId(cartDetail.getId());
//        cartDetailResponse.setQuantity(cartDetail.getQuantity());
//        cartDetailResponse.setShoeDetail(mapToShoeDetailResponse(cartDetail.getShoeDetail()));
//        return cartDetailResponse;
//    }

//    private ShoeDetailResponse mapToShoeDetailResponse(ShoeDetail shoeDetail) {
//        ShoeDetailResponse shoeDetailResponse = new ShoeDetailResponse();
//        shoeDetailResponse.setId(shoeDetail.getId());
//        shoeDetailResponse.setPrice(shoeDetail.getPrice());
//        shoeDetailResponse.setStatus(shoeDetail.getStatus());
//        shoeDetailResponse.setQuantity(shoeDetail.getQuantity());
//        return shoeDetailResponse;
//    }
}
