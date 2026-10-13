package com.clicktrack.service;

import com.clicktrack.dto.OffreDto;

import java.util.List;

public interface OffreService {
    List<OffreDto> getOffresByPublisher(Long publisherId);
}