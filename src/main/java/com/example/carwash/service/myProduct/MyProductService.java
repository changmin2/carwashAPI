package com.example.carwash.service.myProduct;

import com.example.carwash.domain.member.MyProduct;
import com.example.carwash.repository.myProduct.MyProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyProductService {

    private final MyProductRepository myProductRepository;

    @Transactional
    public MyProduct registerMyProduct(MyProduct myProduct)  {
        MyProduct re = myProductRepository.save(
                MyProduct.builder()
                        .memberId(myProduct.getMemberId())
                        .productName(myProduct.getProductName())
                        .category(myProduct.getCategory())
                        .cycle(myProduct.getCycle())
                        .imgUrl(myProduct.getImgUrl())
                        .link(myProduct.getLink())
                        .build()
        );

        return re;
    }

    public List<MyProduct> getMyProduct(String memberId) {
        return myProductRepository.getMyProduct(memberId);

    }
}
