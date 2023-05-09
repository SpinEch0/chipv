// import Mill dependency
import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import scalalib._
// support BSP
import mill.bsp._

//https://github.com/chipsalliance/cde.gi
object cdeHello extends cde.build.cde("2.13.10")  {
  override def millSourcePath = os.pwd / "cde" / "cde"
}

//import publish._ //import PublishModule & PomSetting
import $file.cde.build  //find cde/build.sc

object chipv extends SbtModule { m =>
  override def millSourcePath = os.pwd
  override def scalaVersion = "2.13.8"
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-P:chiselplugin:genBundleElements"
  )
  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:3.5.4",
  )
  override def scalacPluginIvyDeps = Agg(
    ivy"edu.berkeley.cs:::chisel3-plugin:3.5.4",
  )

  override def moduleDeps = super.moduleDeps ++ Seq(cdeHello)

  object test extends Tests with ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"edu.berkeley.cs::chiseltest:0.5.4"
    )
  }
}
