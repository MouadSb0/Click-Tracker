package com.clicktrack.repository;

import com.clicktrack.model.Lien;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class LienRepositoryImpl implements LienRepository {
    private final List<Lien> liens = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public synchronized Lien save(Lien lien) {
        if (lien.getId() == null) {
            lien.setId(idCounter.incrementAndGet());
        } else {
            idCounter.accumulateAndGet(lien.getId(), Math::max);
        }

        for (int index = 0; index < liens.size(); index++) {
            if (liens.get(index).getId().equals(lien.getId())) {
                liens.set(index, lien);
                return lien;
            }
        }

        liens.add(lien);
        return lien;
    }

    @Override
    public synchronized Optional<Lien> findById(Long id) {
        return liens.stream()
                .filter(lien -> lien.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized List<Lien> findByPublisherId(Long publisherId) {
        List<Lien> result = new ArrayList<>();
        for (Lien lien : liens) {
            if (lien.getPublisherId().equals(publisherId)) {
                result.add(lien);
            }
        }
        return result;
    }

    @Override
    public synchronized int incrementClicks(Long id) {
        for (Lien lien : liens) {
            if (lien.getId().equals(id)) {
                int clicks = lien.getClicks() + 1;
                lien.setClicks(clicks);
                return clicks;
            }
        }
        throw new IllegalArgumentException("Lien not found: " + id);
    }
}