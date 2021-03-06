package scala.macros
package trees

private[macros] trait Trees extends Abstracts with Companions with Extensions { self: Universe =>

  type Tree >: Null <: AnyRef // Tree.pos, Tree.syntax, Tree.structure

  type Ref >: Null <: Tree
  type Stat >: Null <: Tree

  type Name >: Null <: Ref // Name.value
  val Name: NameCompanion = new NameCompanion {}
  private[macros] trait NameCompanion {
    def apply(value: String) = abstracts.nameApply(value)
    def unapply(tree: Any) = abstracts.nameUnapply(tree)
    def Anonymous = abstracts.NameAnonymous
    def Indeterminate = abstracts.NameIndeterminate
  }

  type Lit >: Null <: Term with Type with Pat // Lit.value
  val Lit: LitCompanion = new LitCompanion {}
  private[macros] trait LitCompanion {
    def unapply(tree: Any) = abstracts.litUnapply(tree)
    def Unit = abstracts.LitUnit
    def Boolean = abstracts.LitBoolean
    def Byte = abstracts.LitByte
    def Short = abstracts.LitShort
    def Char = abstracts.LitChar
    def Int = abstracts.LitInt
    def Float = abstracts.LitFloat
    def Long = abstracts.LitLong
    def Double = abstracts.LitDouble
    def String = abstracts.LitString
    def Symbol = abstracts.LitSymbol
    def Null = abstracts.LitNull
  }

  type Term >: Null <: Stat
  val Term: TermCompanion = new TermCompanion {}
  private[macros] trait TermCompanion { // Term.fresh
    type Ref >: Null <: Term with self.Ref
    def This = abstracts.TermThis
    def Super = abstracts.TermSuper
    type Name >: Null <: self.Name with Term.Ref with Pat
    def Name = abstracts.TermName
    def Select = abstracts.TermSelect
    def Interpolate = abstracts.TermInterpolate
    def Xml = abstracts.TermXml
    def Apply = abstracts.TermApply
    def ApplyType = abstracts.TermApplyType
    def ApplyInfix = abstracts.TermApplyInfix
    def ApplyUnary = abstracts.TermApplyUnary
    def Assign = abstracts.TermAssign
    def Return = abstracts.TermReturn
    def Throw = abstracts.TermThrow
    def Ascribe = abstracts.TermAscribe
    def Annotate = abstracts.TermAnnotate
    def Tuple = abstracts.TermTuple
    def Block = abstracts.TermBlock
    def If = abstracts.TermIf
    def Match = abstracts.TermMatch
    def Try = abstracts.TermTry
    def TryWithHandler = abstracts.TermTryWithHandler
    def Function = abstracts.TermFunction
    def PartialFunction = abstracts.TermPartialFunction
    def While = abstracts.TermWhile
    def Do = abstracts.TermDo
    def For = abstracts.TermFor
    def ForYield = abstracts.TermForYield
    def New = abstracts.TermNew
    def NewAnonymous = abstracts.TermNewAnonymous
    def Placeholder = abstracts.TermPlaceholder
    def Eta = abstracts.TermEta
    def Repeated = abstracts.TermRepeated
    type Param >: Null <: Member.Term
    def Param = abstracts.TermParam
  }

  type Type >: Null <: Tree
  val Type: TypeCompanion = new TypeCompanion {}
  private[macros] trait TypeCompanion { // Type.fresh
    type Ref >: Null <: Type with self.Ref
    type Name >: Null <: self.Name with Type.Ref
    def Name = abstracts.TypeName
    def Select = abstracts.TypeSelect
    def Project = abstracts.TypeProject
    def Singleton = abstracts.TypeSingleton
    def Apply = abstracts.TypeApply
    def ApplyInfix = abstracts.TypeApplyInfix
    def Function = abstracts.TypeFunction
    def Tuple = abstracts.TypeTuple
    def With = abstracts.TypeWith
    def And = abstracts.TypeAnd
    def Or = abstracts.TypeOr
    def Refine = abstracts.TypeRefine
    def Existential = abstracts.TypeExistential
    def Annotate = abstracts.TypeAnnotate
    def Placeholder = abstracts.TypePlaceholder
    type Bounds >: Null <: Tree
    def Bounds = abstracts.TypeBounds
    def ByName = abstracts.TypeByName
    def Repeated = abstracts.TypeRepeated
    type Var >: Null <: Type with Member.Type
    def Var = abstracts.TypeVar
    def Method = abstracts.TypeMethod
    def Lambda = abstracts.TypeLambda
    type Param >: Null <: Member.Type
    def Param = abstracts.TypeParam
  }

  type Pat >: Null <: Tree
  val Pat: PatCompanion = new PatCompanion {}
  private[macros] trait PatCompanion { // Pat.fresh
    type Var >: Null <: Pat with Member.Term
    def Var = abstracts.PatVar
    def Wildcard = abstracts.PatWildcard
    def SeqWildcard = abstracts.PatSeqWildcard
    def Bind = abstracts.PatBind
    def Alternative = abstracts.PatAlternative
    def Tuple = abstracts.PatTuple
    def Extract = abstracts.PatExtract
    def ExtractInfix = abstracts.PatExtractInfix
    def Interpolate = abstracts.PatInterpolate
    def Xml = abstracts.PatXml
    def Typed = abstracts.PatTyped
  }

  type Member >: Null <: Tree
  val Member: MemberCompanion = new MemberCompanion {}
  private[macros] trait MemberCompanion {
    type Term >: Null <: Member
    type Type >: Null <: Member
  }

  type Decl >: Null <: Stat
  val Decl: DeclCompanion = new DeclCompanion {}
  private[macros] trait DeclCompanion {
    type Val >: Null <: Decl
    def Val = abstracts.DeclVal
    type Var >: Null <: Decl
    def Var = abstracts.DeclVar
    type Def >: Null <: Decl with Member.Term
    def Def = abstracts.DeclDef
    type Type >: Null <: Decl with Member.Type
    def Type = abstracts.DeclType
  }

  type Defn >: Null <: Stat
  val Defn: DefnCompanion = new DefnCompanion {}
  private[macros] trait DefnCompanion {
    type Val >: Null <: Defn
    def Val = abstracts.DefnVal
    type Var >: Null <: Defn
    def Var = abstracts.DefnVar
    type Def >: Null <: Defn with Member.Term
    def Def = abstracts.DefnDef
    type Macro >: Null <: Defn with Member.Term
    def Macro = abstracts.DefnMacro
    type Type >: Null <: Defn with Member.Type
    def Type = abstracts.DefnType
    type Class >: Null <: Defn with Member.Type
    def Class = abstracts.DefnClass
    type Trait >: Null <: Defn with Member.Type
    def Trait = abstracts.DefnTrait
    type Object >: Null <: Defn with Member.Term
    def Object = abstracts.DefnObject
  }

  type Pkg >: Null <: Member.Term with Stat // Pkg.name
  val Pkg: PkgCompanion = new PkgCompanion {}
  private[macros] trait PkgCompanion {
    def apply(ref: Term.Ref, stats: List[Stat]) = abstracts.PkgProper.apply(ref, stats)
    def unapply(tree: Any): Option[(Term.Ref, List[Stat])] = abstracts.PkgProper.unapply(tree)
    type Object >: Null <: Member.Term with Stat
    def Object = abstracts.PkgObject
  }

  type Ctor >: Null <: Member
  val Ctor: CtorCompanion = new CtorCompanion {}
  private[macros] trait CtorCompanion {
    type Primary >: Null <: Ctor
    def Primary = abstracts.CtorPrimary
    type Secondary >: Null <: Ctor with Stat
    def Secondary = abstracts.CtorSecondary
  }

  type Init >: Null <: Ref
  def Init = abstracts.Init

  type Self >: Null <: Member
  def Self = abstracts.Self

  type Template >: Null <: Tree
  def Template = abstracts.Template

  type Mod >: Null <: Tree
  val Mod: ModCompanion = new ModCompanion {}
  private[macros] trait ModCompanion {
    def Annot = abstracts.ModAnnot
    def Private = abstracts.ModPrivate
    def Protected = abstracts.ModProtected
    def Implicit = abstracts.ModImplicit
    def Final = abstracts.ModFinal
    def Sealed = abstracts.ModSealed
    def Override = abstracts.ModOverride
    def Case = abstracts.ModCase
    def Abstract = abstracts.ModAbstract
    def Covariant = abstracts.ModCovariant
    def Contravariant = abstracts.ModContravariant
    def Lazy = abstracts.ModLazy
    def ValParam = abstracts.ModValParam
    def VarParam = abstracts.ModVarParam
    def Macro = abstracts.ModMacro
  }

  type Enumerator >: Null <: Tree
  val Enumerator: EnumeratorCompanion = new EnumeratorCompanion {}
  private[macros] trait EnumeratorCompanion {
    def Generator = abstracts.EnumeratorGenerator
    def Val = abstracts.EnumeratorVal
    def Guard = abstracts.EnumeratorGuard
  }

  type Import >: Null <: Stat
  def Import = abstracts.Import

  type Importer >: Null <: Tree
  def Importer = abstracts.Importer

  type Importee >: Null <: Ref
  val Importee: ImporteeCompanion = new ImporteeCompanion {}
  private[macros] trait ImporteeCompanion {
    def Wildcard = abstracts.ImporteeWildcard
    def Name = abstracts.ImporteeName
    def Rename = abstracts.ImporteeRename
    def Unimport = abstracts.ImporteeUnimport
  }

  type Case >: Null <: Tree
  def Case = abstracts.Case

  type Source >: Null <: Tree
  def Source = abstracts.Source
}
