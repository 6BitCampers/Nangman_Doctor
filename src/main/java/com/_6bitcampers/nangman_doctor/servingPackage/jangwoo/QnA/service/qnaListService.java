package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.service;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.pagingInfo;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto.qnaBoard;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.mapper.qnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class qnaListService {
    final qnaMapper mapper;

    public pagingInfo<qnaBoard> getBoards(int pageIndex, int pageSize) {
        int totalCount = mapper.getTotalCount();
        List<qnaBoard> list = mapper.findAllBoard((pageIndex - 1) * pageSize, pageSize);

        return new pagingInfo<>(pageIndex, pageSize, totalCount, list);
    }
}
