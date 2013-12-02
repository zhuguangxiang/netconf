package org.opendaylight.controller.sal.restconf.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;

import javax.ws.rs.WebApplicationException;

import org.junit.*;
import org.opendaylight.yangtools.yang.model.api.*;

public class ToJsonLeafrefType {
    private static Set<Module> modules;
    private static DataSchemaNode dataSchemaNode;

    @BeforeClass
    public static void initialization() {
        modules = TestUtils.resolveModules("/yang-to-json-conversion/leafref");
        assertEquals(2, modules.size());
        Module module = TestUtils.resolveModule("main-module", modules);
        assertNotNull(module);
        dataSchemaNode = TestUtils.resolveDataSchemaNode(module, "cont");
        assertNotNull(dataSchemaNode);

    }

    @Test
    public void leafrefAbsolutePathToExistingLeafTest() {
        String json = null;
        try {
            json = TestUtils.writeCompNodeWithSchemaContextToJson(TestUtils
                    .loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_absolut_ref_to_existing_leaf.xml"),
                    "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern.compile(".*\"lf3\":\\p{Blank}*true.*",
                java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

    @Test
    public void leafrefRelativePathToExistingLeafTest() {
        String json = null;
        try {
            json = TestUtils.writeCompNodeWithSchemaContextToJson(TestUtils
                    .loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_relativ_ref_to_existing_leaf.xml"),
                    "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern.compile(".*\"lf2\":\\p{Blank}*121.*",
                java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

    /**
     * Tests case when reference to not existing element is present. In this
     * case value from single node is printed as string.
     */
    @Test
    public void leafrefToNonExistingLeafTest() {
        String json = null;
        try {
            json = TestUtils.writeCompNodeWithSchemaContextToJson(TestUtils
                    .loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_ref_to_non_existing_leaf.xml"),
                    "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern.compile(".*\"lf5\":\\p{Blank}*\"137\".*",
                java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

    /**
     * Tests case when non leaf element is referenced. In this case value from
     * single node is printed as string.
     */
    @Test
    public void leafrefToNotLeafTest() {
        String json = null;
        try {
            json = TestUtils.writeCompNodeWithSchemaContextToJson(
                    TestUtils.loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_ref_to_not_leaf.xml"),
                    "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern.compile(
                ".*\"cont-augment-module\\p{Blank}*:\\p{Blank}*lf6\":\\p{Blank}*\"44.33\".*",
                java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

    /**
     * Tests case when leaflist element is refers to leaf.
     */
    @Test
    public void leafrefFromLeafListToLeafTest() {
        String json = null;
        try {
            json = TestUtils
                    .writeCompNodeWithSchemaContextToJson(
                            TestUtils
                                    .loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_relativ_ref_from_leaflist_to_existing_leaf.xml"),
                            "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern
                .compile(
                        ".*\"cont-augment-module\\p{Blank}*:\\p{Blank}*lflst1\":\\p{Blank}*.*345,\\p{Space}*346,\\p{Space}*347.*",
                        java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

    /**
     * Tests case when leaflist element is refers to leaf.
     */
    @Test
    public void leafrefFromLeafrefToLeafrefTest() {
        String json = null;
        try {
            json = TestUtils.writeCompNodeWithSchemaContextToJson(TestUtils
                    .loadCompositeNode("/yang-to-json-conversion/leafref/xml/data_from_leafref_to_leafref.xml"),
                    "/yang-to-json-conversion/leafref/xml", modules, dataSchemaNode);
        } catch (WebApplicationException | IOException e) {
            // shouldn't end here
            assertTrue(false);
        }
        assertNotNull(json);
        java.util.regex.Pattern ptrn = java.util.regex.Pattern.compile(
                ".*\"cont-augment-module\\p{Blank}*:\\p{Blank}*lf7\":\\p{Blank}*200.*", java.util.regex.Pattern.DOTALL);
        Matcher mtch = ptrn.matcher(json);
        assertTrue(mtch.matches());
    }

}
