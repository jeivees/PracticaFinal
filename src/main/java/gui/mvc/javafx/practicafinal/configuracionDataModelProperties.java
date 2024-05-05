package gui.mvc.javafx.practicafinal;

import javafx.beans.property.*;

public class configuracionDataModelProperties {

        //Modelo de datos original
        protected configuracionDataModel original;

        private IntegerProperty TurnosVidaIniciales = new SimpleIntegerProperty();
        private IntegerProperty ProbReproIndividuo = new SimpleIntegerProperty();
        private IntegerProperty ProbClonIndividuo = new SimpleIntegerProperty();

        public configuracionDataModelProperties(configuracionDataModel original){
            setOriginal(original);
        }

        public void commit(){
            original.setTurnosVidaIniciales(TurnosVidaIniciales.get());
            original.setProbReproIndividuo(ProbReproIndividuo.get());
            original.setProbClonIndividuo(ProbClonIndividuo.get());
        }

        public void rollback(){
            TurnosVidaIniciales.set(original.getTurnosVidaIniciales());
            ProbReproIndividuo.set(original.getProbReproIndividuo());
            ProbClonIndividuo.set(original.getProbClonIndividuo());
        }

        public configuracionDataModel getOriginal(){
            return original;
        }

        public void setOriginal(configuracionDataModel original){
            this.original = original;
            rollback(); //Se inicializan los properties.

        }

        public Property<Integer> TurnosVidaInicialesProperty() {
            return TurnosVidaIniciales.asObject();
        }

        public Property<Number> ProbReproIndividuoProperty() {
            return ProbReproIndividuo;
        }

        public Property<Number> ProbClonIndividuoProperty() {
            return ProbClonIndividuo;
        }
    }