import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import org.json4s.jackson.JsonMethods

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source


object Process {
  def main(args: Array[String]): Unit = {
    // Configuration du Spark Context pour le RDD
    val conf=new SparkConf().setMaster("local").setAppName("App");
    val sc=new SparkContext(conf);
    implicit val formats = DefaultFormats;

    // Parse des données Json et récupération des monstres dans un tableau
    val data = Source.fromFile("Monsters.txt").mkString;
    var json = JsonMethods.parse(data);

    // On met les monstres possédants des sorts dans notre nouveau tableau
    var monsters = new ArrayBuffer[Monster]();
    for(JObject(element)<-json) {
      if(element.size == 2) {
        var name = element(0)._2.extract[String];
        var spells = element(1)._2.extract[List[String]];
        var monster = new Monster(name,spells);
        monsters.append(monster);
      }
    }

    // Lance les traitements sur le rdd (flatmap et reducebykey)
    val rdd = sc.parallelize(monsters);
    var spells = rdd.flatMap(m=>map(m));
    var file = spells.reduceByKey((a,b) =>a+b).map(f=>(f._1._1,f._1._2)).groupByKey();

    // Enregistre un dossier comportant un fichier texte par coeur car le rdd est divisé
    //file.saveAsTextFile("ReversedIndexing.txt");
  }

  // Fonction qui effectue l'indexation inversée (spell -> monsters)
  def map(m:Monster):List[((String,String),Int)] = {
    var result = new ListBuffer[((String,String),Int)]();
    for(spell<-m.spells) {
      var res = ((spell,m.name),1);
      result.append(res);
    }
    return result.toList;
  }
}
