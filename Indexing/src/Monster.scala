case class Monster(n:String,s:List[String]) {
  var name=n;
  var spells=s;

  override def toString = s"Monster($name, $spells)"
}
