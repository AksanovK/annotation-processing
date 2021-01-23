import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDefaultEncoding("UTF-8");
            try {
                configuration.setTemplateLoader(new FileTemplateLoader(new File("D:\\javaNEW\\src\\projects\\Annotations\\src\\main\\resources\\ftlh")));
                String nameOfFtlh = element.getSimpleName().toString() + ".ftlh";
                Template template = configuration.getTemplate(nameOfFtlh);
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                Form form = new Form(annotation.method(), annotation.action());
                List<? extends Element> elems = element.getEnclosedElements();
                List<Input> inputs = new ArrayList<>();
                for (Element value : elems) {
                    HtmlInput annotation1 = value.getAnnotation(HtmlInput.class);
                    if (annotation1 != null) {
                        Input input = new Input(annotation1.type(), annotation1.name(), annotation1.placeholder());
                        inputs.add(input);
                    }
                }
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("inputs", inputs);
                attributes.put("form", form);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile().getAbsolutePath()));
                    template.process(attributes, writer);
                } catch (IOException | TemplateException e) {
                    throw new IllegalStateException(e);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return true;
    }
}
