package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.model.PictureData;

import java.util.Optional;

@Service
public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    PictureData createPictureData(byte[] picture);
}
