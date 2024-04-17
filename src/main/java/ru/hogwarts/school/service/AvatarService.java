package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.AvatarProcessingException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final AvatarMapper avatarMapper;
    private final Path pathToAvatarsDir;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository, AvatarMapper avatarMapper,
                         @Value("${application.path-to-avatars-dir}") String pathToAvatarsDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarMapper = avatarMapper;
        this.pathToAvatarsDir = Paths.get(pathToAvatarsDir);
    }

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(pathToAvatarsDir) || !Files.isDirectory(pathToAvatarsDir)) {
                Files.createDirectories(pathToAvatarsDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadAvatar(long studentId, MultipartFile image) {
        logger.info("Was invoked method uploadAvatar");
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException(studentId));
            byte[] data = image.getBytes();
            String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
            String fileName = String.format("%s.%s", UUID.randomUUID(), extension);
            Path path = pathToAvatarsDir.resolve(fileName);
            Files.write(path, data);


            Avatar avatar = new Avatar();
            avatar.setStudent(student);
            avatar.setData(data);
            avatar.setFileSize(image.getSize());
            avatar.setMediaType(image.getContentType());
            avatar.setFilePath(path.toString());

            avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new AvatarProcessingException(e);
        }

    }

    public Pair<byte[], String> getAvatarFromDb(long studentId) {
        logger.info("Was invoked method getAvatarFromDb");
        Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                .orElseThrow(() -> new AvatarNotFoundException(studentId));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Pair<byte[], String> getAvatarFromFs(long studentId) {
        logger.info("Was invoked method getAvatarFromFs");
        try {
            Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                    .orElseThrow(() -> new AvatarNotFoundException(studentId));
            byte[] data = Files.readAllBytes(Paths.get(avatar.getFilePath()));
            return Pair.of(data, avatar.getMediaType());
        } catch (IOException e) {
            throw new AvatarProcessingException(e);
        }

    }

    public List<AvatarDto> getAvatars(int page, int size) {
        logger.info("Was invoked method getAvatars");
        return avatarRepository.findAll(PageRequest.of(page - 1, size)).get()
                .map(avatarMapper::toDto)
                .collect(Collectors.toList());
    }
}
