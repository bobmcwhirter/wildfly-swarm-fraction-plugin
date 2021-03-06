package org.wildfly.swarm.plugin.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Function;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.wildfly.swarm.plugin.FractionMetadata;

/**
 * @author Ken Finnigan
 */
public class CDIMarker implements Function<FractionMetadata,FractionMetadata> {

    public static final String CDI_PROPERTY = "swarm.fraction.cdi";

    public static final String CDI_MARKER = "META-INF/beans.xml";

    public CDIMarker(Log log, MavenProject project) {
        this.log = log;
        this.project = project;
    }

    public FractionMetadata apply(FractionMetadata meta) {
        if (  meta.hasJavaCode() ) {
            File cdiMarker = new File(this.project.getBuild().getOutputDirectory(), CDI_MARKER);
            cdiMarker.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(cdiMarker)) {
                writer.write("");
                writer.flush();
            } catch (IOException e) {
                this.log.error(e.getMessage(), e);
            }
        }

        return meta;
    }

    private final MavenProject project;
    private final Log log;
}
