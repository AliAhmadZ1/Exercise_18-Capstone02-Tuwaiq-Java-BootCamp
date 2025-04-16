package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import com.example.capstone02_bookfriend.Model.Groups;
import com.example.capstone02_bookfriend.Model.Club;
import com.example.capstone02_bookfriend.Model.Category;
import com.example.capstone02_bookfriend.Repository.CategoryRepository;
import com.example.capstone02_bookfriend.Repository.ClubRepository;
import com.example.capstone02_bookfriend.Repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public void addClub(Club club) {
        Groups groups = groupRepository.findGroupById(club.getGroup_id());
        Category category = categoryRepository.findCategoryById(club.getCategory_id());
        if (groups==null||category==null)
            throw new ApiException("group or category not found");
        clubRepository.save(club);

    }

    public void updateClub(Integer id, Club club) {
        Groups groups = groupRepository.findGroupById(club.getGroup_id());
        Category category = categoryRepository.findCategoryById(club.getCategory_id());
        if (groups==null||category==null)
            throw new ApiException("group or category not found");
        Club oldClub = clubRepository.findClubById(id);
        if (oldClub == null)
            throw new ApiException("not found");

        oldClub.setCategory_id(club.getCategory_id());
        oldClub.setName(club.getName());
        oldClub.setGroup_id(club.getGroup_id());
        oldClub.setNumber_of_groups(club.getNumber_of_groups());
        clubRepository.save(oldClub);

    }

    public void deleteClub(Integer id){
        Club club = clubRepository.findClubById(id);
        if (club==null)
            throw new ApiException("club not found");
        clubRepository.delete(club);

    }
}
