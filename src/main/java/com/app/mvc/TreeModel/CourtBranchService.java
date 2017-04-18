package com.app.mvc.TreeModel;

import com.app.mvc.OntologyModelDao.StardogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourtBranchService {

    @Autowired
    StardogDao stardogDAO;
    public CourtBranchService() {

    }

    public List<CourtBranch> findCourtBranchByParentId(int id) {
        List<CourtBranch> childElements = new ArrayList<CourtBranch>();
            List<CourtBranch> allElements = stardogDAO.makeOntTree();

        for (final CourtBranch element : allElements) {
            if (element.getParentId() == id) {
                childElements.add(element);
            }
        }

        return childElements;
    }


}

