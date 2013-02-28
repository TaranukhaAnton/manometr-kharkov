package org.krams.tutorial.util;

import org.krams.tutorial.view.BaseTreeNode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SubstrTagHandler extends TagSupport {
    private static String TYPE_GROUP = "UnitView";


    BaseTreeNode rootTreeNode;

    public void setRootTreeNode(BaseTreeNode rootTreeNode) {
        this.rootTreeNode = rootTreeNode;
    }


    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            printFirst(out, rootTreeNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }


    private void print(JspWriter out, BaseTreeNode treeNode) throws IOException {
        out.println("<li>");
        if (TYPE_GROUP.equals(treeNode.getType())) {
            out.println("<span onclick=\"selectGroup("+ treeNode.getId()+")\"  class=\"file\">" + treeNode.getDescr() + "</span>");
        } else {
            out.println("<span   class=\"folder\">" + treeNode.getDescr() + "</span>");
        }
        List<BaseTreeNode> children = treeNode.getChildren();
        if (!children.isEmpty()) {
            out.println("<ul>");
        }
        for (BaseTreeNode child : children) {
            print(out, child);
        }
        if (!children.isEmpty()) {
            out.println("</ul>");
        }
        out.println("</li>");
    }

    private void printFirst(JspWriter out, BaseTreeNode treeNode) throws IOException {
        out.println("<ul id=\"browser\" class=\"filetree treeview-famfamfam\">");
        for (BaseTreeNode child : treeNode.getChildren()) {
            print(out, child);
        }
        out.println("</ul>");
    }
}