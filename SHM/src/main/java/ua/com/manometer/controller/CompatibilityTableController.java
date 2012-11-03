package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.modeldescription.ModelDescription;
import ua.com.manometer.service.modeldescription.ModelDescriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
@RequestMapping("/compatibility")
public class CompatibilityTableController {
    @Autowired

    ModelDescriptionService modelDescriptionService;

    @RequestMapping("/co")
    public String coForward(Map<String, Object> map) {
        return "redactCo";
    }

    @RequestMapping("/ao")
    public String aoForward(Map<String, Object> map) {
        return "redactAo";
    }

    @RequestMapping("/op")
    public String opForward(Map<String, Object> map) {
        return "redactOp";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String redactModelDescription(HttpServletRequest request) {
        System.out.println("CompatibilityTableController.redactModelDescription");
        try {
            String elements = request.getParameter("elements");
            List<ModelDescription> list = getModelDescriptions(elements);
            String param = request.getParameter("param");
            if (param.equals("limits")) {
                String loLim = request.getParameter("loLim");
                String hiLim = request.getParameter("hiLim");
                //   System.out.println(loLim+"--"+hiLim);
                if (loLim != null && loLim != "" && loLim != "undefined" && hiLim != null && hiLim != "" && hiLim != "undefined") {
                    Integer h = new Integer(hiLim);
                    Integer l = new Integer(loLim);
                    for (ModelDescription item : list) {
                        item.setLoLimit(l);
                        item.setHiLimit(h);

                        modelDescriptionService.updateDescription(item);
                    }
                }


            } else if (param.equals("isp")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setIsp(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("mat")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setMaterials(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("err")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setErrors(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("stat")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setStaticPressures(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("Out")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setOutputSygnals(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("DU")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setDU(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("KMCH")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setKMCH(val);
                    modelDescriptionService.updateDescription(item);
                }
            } else if (param.equals("VM")) {
                String val = request.getParameter("val");
                Boolean b = new Boolean(val);
                for (ModelDescription item : list) {
                    item.setVM(b);
                    modelDescriptionService.updateDescription(item);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }


    private List<ModelDescription> getModelDescriptions(String models) {
        List<Long> modelIds = new LinkedList<Long>();
        StringTokenizer tokenizer = new StringTokenizer(models, "|", false);
        for (; tokenizer.hasMoreTokens(); ) {
            String model = tokenizer.nextToken();
            modelIds.add(new Long(model));
        }
        List<ModelDescription> result = modelDescriptionService.findListByIds(modelIds);
        return result;
    }


}
