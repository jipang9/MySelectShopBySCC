package SKT.seletctshop.service;

import SKT.seletctshop.entity.Folder;
import SKT.seletctshop.entity.Product;
import SKT.seletctshop.entity.User;
import SKT.seletctshop.repository.FolderRepository;
import SKT.seletctshop.repository.ProductRepository;
import SKT.seletctshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;


    // 로그인한 회원에 폴더들 등록
    @Transactional
    public List<Folder> addFolders(List<String> folderNames, String name) {

        // 어떠한 이유때문에 코드가 이런 식으로 작성되었는지 고민해볼것.
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            // 이미 생성한 폴더가 아닌 경우만 폴더 생성
            if (!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            }
        }

        return folderRepository.saveAll(folderList);
    }

    // 로그인한 회원이 등록된 모든 폴더 조회
    @Transactional(readOnly = true)
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        // 기존 폴더 리스트에서 folder name 이 있는지?
        for (Folder existFolder : existFolderList) {
            if (existFolder.getName().equals(folderName)) {
                return true;
            }
        }

        return false;
    }

}