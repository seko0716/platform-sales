package net.sergey.kosov.prediction.configuration

import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.classification.GBTClassifier
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.regression.DecisionTreeRegressor
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct

@Configuration
class ChurnPredictionConfiguration {
    /**
     * Случайные леса - популярное семейство методов классификации и регрессии.
     * Более подробную информацию о spark.mlреализации можно найти в разделе о случайных лесах
     * https://spark.apache.org/docs/2.1.0/ml-classification-regression.html#random-forests.
     * */
    @Bean
    @Profile("randomForestClassifier", "default")
    fun randomForestClassifier(): RandomForestClassifier {
        return RandomForestClassifier().setLabelCol("label").setFeaturesCol("features")
    }

    /**
     * Классификатор дерева решений
     * Деревья принятия решений являются популярным семейством методов классификации и регрессии.
     * Более подробную информацию о spark.mlреализации можно найти в разделе о деревьях решений
     * https://spark.apache.org/docs/2.1.0/ml-classification-regression.html#decision-trees
     * */
    @Profile("decisionTreeClassifier")
    @Bean
    fun decisionTreeClassifier(): DecisionTreeClassifier {
        return DecisionTreeClassifier().setLabelCol("label").setFeaturesCol("features")
    }

    /**
     * Градиентный древовидный классификатор
     * Градиентные деревья (GBT) - популярный метод классификации и регрессии с использованием ансамблей деревьев решений.
     * Более подробную информацию о spark.mlреализации можно найти в разделе GBT
     * https://spark.apache.org/docs/2.1.0/ml-classification-regression.html#gradient-boosted-trees-gbts
     * */
    @Profile("gbtClassifier")
    @Bean
    fun gbtClassifier(): GBTClassifier {
        return GBTClassifier().setLabelCol("label").setFeaturesCol("features")
    }

    /**
     * Регрессия дерева решений
     * Деревья принятия решений являются популярным семейством методов классификации и регрессии.
     * Более подробную информацию о spark.mlреализации можно найти в разделе о деревьях решений.
     * https://spark.apache.org/docs/2.1.0/ml-classification-regression.html#decision-trees
     */
    @Profile("decisionTreeRegressor")
    @Bean
    fun decisionTreeRegressor(): DecisionTreeRegressor {
        return DecisionTreeRegressor().setLabelCol("label").setFeaturesCol("features")
    }

    @Bean
    fun sparkSession(sparkProperties: SparkProperties): SparkSession {
        return SparkSession
                .builder()
                .master(sparkProperties.master)
                .appName(sparkProperties.appName)
                .orCreate
    }

    @Bean
    fun sqlContext(sparkProperties: SparkProperties): SQLContext {
        return SQLContext(sparkSession(sparkProperties))
    }

    @Bean
    fun schema(): StructType {
        return StructType(arrayOf(
                StructField("state", `StringType$`.`MODULE$`, true, Metadata.empty()),
                StructField("account_length", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("area_code", `StringType$`.`MODULE$`, true, Metadata.empty()),
                StructField("phone_number", `StringType$`.`MODULE$`, true, Metadata.empty()),
                StructField("intl_plan", `StringType$`.`MODULE$`, true, Metadata.empty()),
                StructField("voice_mail_plan", `StringType$`.`MODULE$`, true, Metadata.empty()),
                StructField("number_vmail_messages", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_day_minutes", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_day_calls", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_day_charge", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_eve_minutes", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_eve_calls", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_eve_charge", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_night_minutes", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_night_calls", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_night_charge", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_intl_minutes", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_intl_calls", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("total_intl_charge", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("number_customer_service_calls", `DoubleType$`.`MODULE$`, true, Metadata.empty()),
                StructField("churned", `StringType$`.`MODULE$`, true, Metadata.empty())))
    }

    @PostConstruct
    fun init() {
        System.setProperty("hadoop.home.dir", "/home/sergey/IdeaProjects/product-ecommendation/conf")//todo разобратся что и зачем это
    }


}