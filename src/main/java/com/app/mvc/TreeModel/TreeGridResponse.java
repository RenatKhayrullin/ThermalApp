package com.app.mvc.TreeModel;

import com.app.mvc.OntologyModelDao.StardogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TreeGridResponse {

    //inject the service to getInstances the id of your model
    @Resource
    @Autowired
    CourtBranchService cbService;

    @Autowired
    StardogDao stardogDAO;

    public TreeGridResponse(){
    }

    /*@Autowired
    private List<CourtBranch> allElements = stardogDAO.makeOntTree();*/

    //returning the tree as a JSON to use AJAX
    public String cbBTreeAsJson(final CourtBranchTree tree){
        final StringBuffer sb = new StringBuffer();
        final CourtBranch root = tree.getRootElement().getData();
        sb.append("[\r {\"title\": \"" + root.getName() + "\", \"key\": \"" + root.getId() + "\", \"children\": [\r");
        final List<Node<CourtBranch>> children = tree.getRootElement().getChildren();
        loopForChildren(sb, children);
        sb.append("]");
        return sb.toString();
    }

    private StringBuffer loopForChildren(final StringBuffer sb, final List<Node<CourtBranch>> children) {
        for (int i = 0; i < children.size(); i++) {
            final Node<CourtBranch> childElement = children.get(i);
            if (i == 0) {
                sb.append("{\"title\": \"" + childElement.getData().getName() + "\", \"key\": \"" + childElement.getData().getId() + "\"");
            } else {
                sb.append(", {\"title\": \"" + childElement.getData().getName() + "\", \"key\": \"" + childElement.getData().getId() + "\"");
            }
            if (childElement.hasChildren()) {
                sb.append(", \"children\": [\r");
                loopForChildren(sb, childElement.getChildren());
            } else {
                sb.append("}");
            }
        }
        sb.append("]}");
        return sb;
    }

    public CourtBranchTree get() {
        final CourtBranchTree tree = new CourtBranchTree();
            List<CourtBranch> allElements = stardogDAO.makeOntTree();
       final Node<CourtBranch> root = new Node<CourtBranch> (allElements.get(0));//gets your root
        getRecursive(root, tree);
        tree.setRootElement(root);
        return tree;
    }

    private void getRecursive(final Node<CourtBranch> courtBranch, final CourtBranchTree tree) {
        final List<CourtBranch> children = this.cbService.findCourtBranchByParentId(courtBranch.getData().getId());
        final List<Node<CourtBranch>> childElements = new ArrayList<Node<CourtBranch>>();
        for (final CourtBranch childCourtBranch : children) {
            final Node<CourtBranch> childElement = new Node<CourtBranch>(childCourtBranch);
            childElements.add(childElement);
            getRecursive(childElement, tree);
        }
        courtBranch.setChildren(childElements);
    }

}