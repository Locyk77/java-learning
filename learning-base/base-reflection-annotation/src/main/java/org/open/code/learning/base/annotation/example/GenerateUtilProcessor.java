package org.open.code.learning.base.annotation.example;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;


/**
 *
 *@author: Locyk
 *@time: 2025/9/11
 *
 */
@SupportedAnnotationTypes("org.open.code.learning.base.annotation.example.GenerateUtil")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class GenerateUtilProcessor extends AbstractProcessor {

    private Messager messager;//用于打印编译期日志
    private Filer filer;//用于生成新文件

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();//日志工具
        filer = processingEnv.getFiler();//文件生成工具
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateUtil.class)) {
            if (!(element instanceof TypeElement)) {
                error(element, "Only classes can be annotated with @GenerateUtil");
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            GenerateUtil annotation = typeElement.getAnnotation(GenerateUtil.class);

            //获取类名和包名
            String className = typeElement.getSimpleName().toString();
            String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
            String utilClassName = className + annotation.suffix();//生成的工具类名

            //生成工具类代码
            generateUtilClass(packageName, utilClassName, className);
        }
        return true;//表示该注解已被处理，无需其他处理器处理
    }

    //生成工具类的Java代码
    private void generateUtilClass(String packageName, String utilClassName, String targetClassName) {
        try {
            // 创建Java文件（包名+类名）
            JavaFileObject file = filer.createClassFile(packageName + "." + utilClassName);
            try (Writer writer = file.openWriter()) {
                // 写入代码（例如：生成一个打印目标类名的工具方法）
                writer.write("package " + packageName + ";\n\n");
                writer.write("public class " + utilClassName + " {\n");
                writer.write("    public static void print" + targetClassName + "() {\n");
                writer.write("        System.out.println(\"Generated util for: " + targetClassName + "\");\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
            info(null, "Generated class: " + packageName + "." + utilClassName);
        } catch (IOException e) {
            error(null, "Failed to generate class: " + e.getMessage());
        }
    }

    // 辅助方法：打印错误日志
    private void error(Element element, String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }

    // 辅助方法：打印提示日志
    private void info(Element element, String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg, element);
    }
}
