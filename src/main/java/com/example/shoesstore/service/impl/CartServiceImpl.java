package com.example.shoesstore.service.impl;

import com.example.shoesstore.dto.response.CartDetailResponse;
import com.example.shoesstore.dto.response.CartResponse;
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
import com.example.shoesstore.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.shoesstore.service.impl.CartDetailServiceImpl.totalPrice;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    UserRepository userRepository;
    ShoeDetailRepository shoeDetailRepository;
    CartDetailRepository cartDetailRepository;
    CartRepository cartRepository;
    CartMapper cartMapper;
    CartDetailMapper cartDetailMapper;

    @Override
    public CartResponse removeShoeDetailFromCart(Long[] idShoeDetails, Long idUser) {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart = user.getCart();
        List<CartDetail> listCartDetail = cart.getCartDetails();

        boolean isFound = false;
        ShoeDetail shoeDetail;
        for (Long id : idShoeDetails) {
            log.info("id shoe detail {} ", id);

            // check shoe detail exist
            shoeDetail = shoeDetailRepository.findById(id)
                    .orElseThrow(() -> {
                        return new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND);
                    });
            isFound = true;

            // check if cart detail has shoe detail
            // -> remove from list and delete from db
            if (isFound == true) {
                for (CartDetail x : listCartDetail) {
                    if (x.getShoeDetail() == shoeDetail) {
                        listCartDetail.remove(x);
                        cartDetailRepository.delete(x);
                        break;
                    }
                }
            }
        }

        // set lại giá trị cart
        Long totalPrice = totalPrice(listCartDetail);
        cart.setTotalPrice(totalPrice);
        cart.setCartDetails(listCartDetail);

        Cart cart1 = cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.mapToResponse(cart1);
        cartResponse.setListCartDetail(getListCartDetail(listCartDetail));

        return cartResponse;
    }

    @Override
    public CartResponse clearCart(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart = user.getCart();
        List<CartDetail> listCartDetail = cart.getCartDetails();
        for (CartDetail x : listCartDetail) {
            listCartDetail.remove(x);
            cartDetailRepository.delete(x);
        }
        cart.setTotalPrice(0L);
        cart.setCartDetails(listCartDetail);

        Cart cart1 = cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.mapToResponse(cart1);
        cartResponse.setListCartDetail(getListCartDetail(listCartDetail));

        return cartResponse;
    }

    public List<CartDetailResponse> getListCartDetail(List<CartDetail> listCartDetail) {
        return listCartDetail.stream()
                .map(cartDetail1 -> cartDetailMapper.mapToResponse(cartDetail1))
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse getAll(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart = user.getCart();
        List<CartDetail> listCartDetail = cart.getCartDetails();

        CartResponse cartResponse = cartMapper.mapToResponse(cart);
        cartResponse.setListCartDetail(getListCartDetail(listCartDetail));
        return cartResponse;
    }
}
