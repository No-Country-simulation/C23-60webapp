package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.DetailsPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsPurchaseService {


    private final DetailsPurchaseRepository detailsPurchaseRepository;
    private final AuthService authService;

    @Autowired
    public DetailsPurchaseService(DetailsPurchaseRepository detailsPurchaseRepository, AuthService authService) {
        this.detailsPurchaseRepository = detailsPurchaseRepository;
        this.authService = authService;

    }


    public List<DetailsPurchaseDTO> getDetailsByPurchaseId(Long purchaseId, String username) {
        User user = authService.getUser();
        // Verificar que la compra pertenece al usuario autenticado
        List<DetailsPurchase> details = detailsPurchaseRepository.findByPurchase_IdAndPurchase_User_Id(purchaseId, user.getId());
        return DetailsPurchaseMapper.toDTOList(details);

    }


    public DetailsPurchaseDTO getDetailById(Long detailId, String username) {
        User user = authService.getUser();
        DetailsPurchase detail = detailsPurchaseRepository.findByIdAndPurchase_User_Id(detailId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DetailsPurchase", "ID", detailId));
        return new DetailsPurchaseDTO(detail);
    }
}
