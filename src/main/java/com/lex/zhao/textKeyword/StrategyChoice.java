package com.lex.zhao.textKeyword;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
/**
 * Created by qtfs on 2018/5/30.
 */
public class StrategyChoice {

    public  void strategyChoice (Map<String, Integer> resultMap) {
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
                .newKnowledgeBuilder();

        // this will parse and compile in one step
        kbuilder.add( ResourceFactory.newClassPathResource( "Network.drl",
                StrategyChoice.class ),
                ResourceType.DRL );

        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
            System.out.println( kbuilder.getErrors().toString() );
            throw new RuntimeException( "Unable to compile \"Network.drl\"." );
        }

        // get the compiled packages (which are serializable)
        final Collection<KnowledgePackage> pkgs = kbuilder
                .getKnowledgePackages();

        // add the packages to a knowledgebase (deploy the knowledge packages).
        final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( pkgs );

        final StatefulKnowledgeSession ksession = kbase
                .newStatefulKnowledgeSession();
//        ksession.setGlobal( "list",
//                new ArrayList<Object>() );

        ksession.addEventListener( new DebugAgendaEventListener() );
        ksession.addEventListener( new DebugWorkingMemoryEventListener() );

        for(String s : resultMap.keySet()) {
            if(s == "bandwidth") {
                final BandWidth bandWidth = new BandWidth();
                bandWidth.setValue(resultMap.get(s));
                ksession.insert(bandWidth);
            }
            if(s == "latency") {
                final Latency latency = new Latency();
                latency.setValue(resultMap.get(s));
                ksession.insert(latency);
            }
            if(s == "utilizationRate") {
                final UtilizationRate utiliaztionRate = new UtilizationRate();
                utiliaztionRate.setRate(resultMap.get(s));
                ksession.insert(utiliaztionRate);
            }
        }

        ksession.fireAllRules();
        ksession.dispose();

    }


    public static class BandWidth{
        int value;

        public BandWidth() {

        }

        public BandWidth(int value) {
            this.value = value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class Latency{
        int latency;

        public Latency() {

        }

        public Latency(int value) {
            this.latency = value;
        }

        public void setValue(int value) {
            this.latency = value;
        }

        public int getValue() {
            return latency;
        }
    }

    public static class UtilizationRate {
        int rate;

        public UtilizationRate() {

        }

        public UtilizationRate(int rate) {
            this.rate = rate;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }


}
