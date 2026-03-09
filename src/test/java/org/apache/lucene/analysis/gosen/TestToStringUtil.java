/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.analysis.gosen;

import org.apache.lucene.tests.util.LuceneTestCase;
import org.junit.Test;

public class TestToStringUtil extends LuceneTestCase {

  @Test
  public void testPOS() {
    assertEquals("noun-suffix-verbal", ToStringUtil.getPOSTranslation("名詞-接尾-サ変接続"));
    assertEquals("noun", ToStringUtil.getPOSTranslation("名詞"));
    assertEquals("verb-main", ToStringUtil.getPOSTranslation("動詞-自立"));
    assertEquals("adjective", ToStringUtil.getPOSTranslation("形容詞"));
    assertEquals("particle", ToStringUtil.getPOSTranslation("助詞"));
    assertEquals("auxiliary-verb", ToStringUtil.getPOSTranslation("助動詞"));
    assertEquals("symbol-period", ToStringUtil.getPOSTranslation("記号-句点"));
    assertEquals("symbol-comma", ToStringUtil.getPOSTranslation("記号-読点"));
    assertEquals("unknown", ToStringUtil.getPOSTranslation("未知語"));
    assertEquals("prefix", ToStringUtil.getPOSTranslation("接頭詞"));
    assertEquals("conjunction", ToStringUtil.getPOSTranslation("接続詞"));
    assertEquals("adnominal", ToStringUtil.getPOSTranslation("連体詞"));
    assertEquals("adverb", ToStringUtil.getPOSTranslation("副詞"));
    assertEquals("interjection", ToStringUtil.getPOSTranslation("感動詞"));
    assertNull(ToStringUtil.getPOSTranslation("unknown-pos"));
    assertNull(ToStringUtil.getPOSTranslation(null));
  }

  @Test
  public void testConjType() {
    assertEquals("*", ToStringUtil.getConjTypeTranslation("*"));
    assertEquals("1-row", ToStringUtil.getConjTypeTranslation("一段"));
    assertEquals("irregular-suru", ToStringUtil.getConjTypeTranslation("サ変・スル"));
    assertEquals("irregular-suffix-suru", ToStringUtil.getConjTypeTranslation("サ変・−スル"));
    assertEquals("irregular-suffix-zuru", ToStringUtil.getConjTypeTranslation("サ変・−ズル"));
    assertEquals("5-row-cons-k-i-onbin", ToStringUtil.getConjTypeTranslation("五段・カ行イ音便"));
    assertEquals("5-row-cons-k-cons-onbin", ToStringUtil.getConjTypeTranslation("五段・カ行促音便"));
    assertEquals("5-row-cons-k-cons-onbin-yuku", ToStringUtil.getConjTypeTranslation("五段・カ行促音便ユク"));
    assertEquals("5-row-cons-s", ToStringUtil.getConjTypeTranslation("五段・サ行"));
    assertEquals("5-row-cons-g", ToStringUtil.getConjTypeTranslation("五段・ガ行"));
    assertEquals("5-row-cons-r", ToStringUtil.getConjTypeTranslation("五段・ラ行"));
    assertEquals("5-row-cons-r-special", ToStringUtil.getConjTypeTranslation("五段・ラ行特殊"));
    assertEquals("5-row-aru", ToStringUtil.getConjTypeTranslation("五段・ラ行アル"));
    assertEquals("5-row-cons-m", ToStringUtil.getConjTypeTranslation("五段・マ行"));
    assertEquals("5-row-cons-t", ToStringUtil.getConjTypeTranslation("五段・タ行"));
    assertEquals("5-row-cons-n", ToStringUtil.getConjTypeTranslation("五段・ナ行"));
    assertEquals("5-row-cons-w-cons-onbin", ToStringUtil.getConjTypeTranslation("五段・ワ行促音便"));
    assertEquals("5-row-cons-w-u-onbin", ToStringUtil.getConjTypeTranslation("五段・ワ行ウ音便"));
    assertEquals("kuru-kana", ToStringUtil.getConjTypeTranslation("カ変・クル"));
    assertEquals("kuru-kanji", ToStringUtil.getConjTypeTranslation("カ変・来ル"));
    assertEquals("non-inflectional", ToStringUtil.getConjTypeTranslation("不変化型"));
    assertEquals("special-desu", ToStringUtil.getConjTypeTranslation("特殊・デス"));
    assertEquals("special-masu", ToStringUtil.getConjTypeTranslation("特殊・マス"));
    assertEquals("special-ta", ToStringUtil.getConjTypeTranslation("特殊・ダ"));
    assertEquals("special-da", ToStringUtil.getConjTypeTranslation("特殊・タ"));
    assertEquals("special-ja", ToStringUtil.getConjTypeTranslation("特殊・ジャ"));
    assertEquals("special-nai", ToStringUtil.getConjTypeTranslation("特殊・ナイ"));
    assertEquals("special-nu", ToStringUtil.getConjTypeTranslation("特殊・ヌ"));
    assertEquals("special-tai", ToStringUtil.getConjTypeTranslation("特殊・タイ"));
    assertEquals("special-ya", ToStringUtil.getConjTypeTranslation("特殊・ヤ"));
    assertEquals("classical-gotoshi", ToStringUtil.getConjTypeTranslation("文語・ゴトシ"));
    assertEquals("classical-ki", ToStringUtil.getConjTypeTranslation("文語・キ"));
    assertEquals("classical-beshi", ToStringUtil.getConjTypeTranslation("文語・ベシ"));
    assertEquals("classical-maji", ToStringUtil.getConjTypeTranslation("文語・マジ"));
    assertEquals("classical-nari", ToStringUtil.getConjTypeTranslation("文語・ナリ"));
    assertEquals("classical-ri", ToStringUtil.getConjTypeTranslation("文語・リ"));
    assertEquals("classical-keri", ToStringUtil.getConjTypeTranslation("文語・ケリ"));
    assertEquals("classical-ru", ToStringUtil.getConjTypeTranslation("文語・ル"));
    assertEquals("adj-group-i", ToStringUtil.getConjTypeTranslation("形容詞・イ段"));
    assertEquals("adj-group-a-o-u", ToStringUtil.getConjTypeTranslation("形容詞・アウオ段"));
    assertEquals("1-row-eru", ToStringUtil.getConjTypeTranslation("一段・得ル"));
    assertEquals("1-row-kureru", ToStringUtil.getConjTypeTranslation("一段・クレル"));
    assertEquals("irregular-cons-r", ToStringUtil.getConjTypeTranslation("ラ変"));
    assertEquals("2-row-lower-cons-t", ToStringUtil.getConjTypeTranslation("下二・タ行"));
    assertEquals("2-row-lower-cons-d", ToStringUtil.getConjTypeTranslation("下二・ダ行"));
    assertEquals("2-row-lower-cons-h", ToStringUtil.getConjTypeTranslation("下二・ハ行"));
    assertEquals("2-row-lower-cons-k", ToStringUtil.getConjTypeTranslation("下二・カ行"));
    assertEquals("2-row-lower-cons-m", ToStringUtil.getConjTypeTranslation("下二・マ行"));
    assertEquals("2-row-lower-cons-g", ToStringUtil.getConjTypeTranslation("下二・ガ行"));
    assertEquals("2-row-lower-u", ToStringUtil.getConjTypeTranslation("下二・得"));
    assertEquals("2-row-upper-cons-d", ToStringUtil.getConjTypeTranslation("上二・ダ行"));
    assertEquals("2-row-upper-cons-h", ToStringUtil.getConjTypeTranslation("上二・ハ行"));
    assertEquals("4-row-cons-t", ToStringUtil.getConjTypeTranslation("四段・タ行"));
    assertEquals("4-row-cons-h", ToStringUtil.getConjTypeTranslation("四段・ハ行"));
    assertEquals("4-row-cons-b", ToStringUtil.getConjTypeTranslation("四段・バ行"));
    assertEquals("4-row-cons-s", ToStringUtil.getConjTypeTranslation("四段・サ行"));
    assertNull(ToStringUtil.getConjTypeTranslation("unknown-type"));
    assertNull(ToStringUtil.getConjTypeTranslation(null));
  }

  @Test
  public void testConjForm() {
    assertEquals("*", ToStringUtil.getConjFormTranslation("*"));
    assertEquals("base", ToStringUtil.getConjFormTranslation("基本形"));
    assertEquals("classical-base", ToStringUtil.getConjFormTranslation("文語基本形"));
    assertEquals("imperfective", ToStringUtil.getConjFormTranslation("未然形"));
    assertEquals("conjunctive", ToStringUtil.getConjFormTranslation("連用形"));
    assertEquals("subjunctive", ToStringUtil.getConjFormTranslation("仮定形"));
    assertEquals("conjunctive-ta-connection", ToStringUtil.getConjFormTranslation("連用タ接続"));
    assertEquals("conjunctive-te-connection", ToStringUtil.getConjFormTranslation("連用テ接続"));
    assertEquals("conjunctive-gozai-connection", ToStringUtil.getConjFormTranslation("連用ゴザイ接続"));
    assertEquals("conjunctive-de-connection", ToStringUtil.getConjFormTranslation("連用デ接続"));
    assertEquals("conjunctive-ni-connection", ToStringUtil.getConjFormTranslation("連用ニ接続"));
    assertEquals("uninflected-connection", ToStringUtil.getConjFormTranslation("体言接続"));
    assertEquals("uninflected-special-connection-2", ToStringUtil.getConjFormTranslation("体言接続特殊２"));
    assertEquals("adnominal-special", ToStringUtil.getConjFormTranslation("体言接続特殊"));
    assertEquals("imperfective-nu-connection", ToStringUtil.getConjFormTranslation("未然ヌ接続"));
    assertEquals("imperfective-u-connection", ToStringUtil.getConjFormTranslation("未然ウ接続"));
    assertEquals("imperfective-special", ToStringUtil.getConjFormTranslation("未然特殊"));
    assertEquals("imperfective-reru-connection", ToStringUtil.getConjFormTranslation("未然レル接続"));
    assertEquals("imperative-e", ToStringUtil.getConjFormTranslation("命令ｅ"));
    assertEquals("imperative-i", ToStringUtil.getConjFormTranslation("命令ｉ"));
    assertEquals("imperative-yo", ToStringUtil.getConjFormTranslation("命令ｙｏ"));
    assertEquals("imperative-ro", ToStringUtil.getConjFormTranslation("命令ｒｏ"));
    assertEquals("garu-connection", ToStringUtil.getConjFormTranslation("ガル接続"));
    assertEquals("modern-base", ToStringUtil.getConjFormTranslation("現代基本形"));
    assertEquals("onbin-base", ToStringUtil.getConjFormTranslation("音便基本形"));
    assertEquals("conditional-contracted-1", ToStringUtil.getConjFormTranslation("仮定縮約１"));
    assertEquals("conditional-contracted-2", ToStringUtil.getConjFormTranslation("仮定縮約２"));
    assertNull(ToStringUtil.getConjFormTranslation("unknown-form"));
    assertNull(ToStringUtil.getConjFormTranslation(null));
  }

  @Test
  public void testHepburn() {
    assertEquals("majan", ToStringUtil.getRomanization("マージャン"));
    assertEquals("uroncha", ToStringUtil.getRomanization("ウーロンチャ"));
    assertEquals("chahan", ToStringUtil.getRomanization("チャーハン"));
    assertEquals("chashu", ToStringUtil.getRomanization("チャーシュー"));
    assertEquals("shumai", ToStringUtil.getRomanization("シューマイ"));
    assertEquals("merodi", ToStringUtil.getRomanization("メロディー"));
  }

  @Test
  public void testRomanizationVowels() {
    assertEquals("a", ToStringUtil.getRomanization("ア"));
    assertEquals("i", ToStringUtil.getRomanization("イ"));
    assertEquals("u", ToStringUtil.getRomanization("ウ"));
    assertEquals("e", ToStringUtil.getRomanization("エ"));
    assertEquals("o", ToStringUtil.getRomanization("オ"));
    assertEquals("ō", ToStringUtil.getRomanization("オウ"));
    // small vowels standalone
    assertEquals("a", ToStringUtil.getRomanization("ァ"));
    assertEquals("i", ToStringUtil.getRomanization("ィ"));
    assertEquals("u", ToStringUtil.getRomanization("ゥ"));
    assertEquals("e", ToStringUtil.getRomanization("ェ"));
    assertEquals("o", ToStringUtil.getRomanization("ォ"));
    // イ combinations
    assertEquals("yi", ToStringUtil.getRomanization("イィ"));
    assertEquals("ye", ToStringUtil.getRomanization("イェ"));
    // ウ combinations
    assertEquals("wa", ToStringUtil.getRomanization("ウァ"));
    assertEquals("wi", ToStringUtil.getRomanization("ウィ"));
    assertEquals("wu", ToStringUtil.getRomanization("ウゥ"));
    assertEquals("we", ToStringUtil.getRomanization("ウェ"));
    assertEquals("wo", ToStringUtil.getRomanization("ウォ"));
    assertEquals("wyu", ToStringUtil.getRomanization("ウュ"));
  }

  @Test
  public void testRomanizationKRow() {
    assertEquals("ka", ToStringUtil.getRomanization("カ"));
    assertEquals("ki", ToStringUtil.getRomanization("キ"));
    assertEquals("ku", ToStringUtil.getRomanization("ク"));
    assertEquals("ke", ToStringUtil.getRomanization("ケ"));
    assertEquals("ko", ToStringUtil.getRomanization("コ"));
    assertEquals("kō", ToStringUtil.getRomanization("コウ"));
    assertEquals("kya", ToStringUtil.getRomanization("キャ"));
    assertEquals("kyu", ToStringUtil.getRomanization("キュ"));
    assertEquals("kyo", ToStringUtil.getRomanization("キョ"));
    assertEquals("kyō", ToStringUtil.getRomanization("キョウ"));
    assertEquals("kyū", ToStringUtil.getRomanization("キュウ"));
    assertEquals("kye", ToStringUtil.getRomanization("キェ"));
    assertEquals("kwa", ToStringUtil.getRomanization("クァ"));
    assertEquals("kwi", ToStringUtil.getRomanization("クィ"));
    assertEquals("kwe", ToStringUtil.getRomanization("クェ"));
    assertEquals("kwo", ToStringUtil.getRomanization("クォ"));
    assertEquals("kwa", ToStringUtil.getRomanization("クヮ"));
  }

  @Test
  public void testRomanizationSRow() {
    assertEquals("sa", ToStringUtil.getRomanization("サ"));
    assertEquals("shi", ToStringUtil.getRomanization("シ"));
    assertEquals("su", ToStringUtil.getRomanization("ス"));
    assertEquals("se", ToStringUtil.getRomanization("セ"));
    assertEquals("so", ToStringUtil.getRomanization("ソ"));
    assertEquals("sō", ToStringUtil.getRomanization("ソウ"));
    assertEquals("sha", ToStringUtil.getRomanization("シャ"));
    assertEquals("shu", ToStringUtil.getRomanization("シュ"));
    assertEquals("sho", ToStringUtil.getRomanization("ショ"));
    assertEquals("shō", ToStringUtil.getRomanization("ショウ"));
    assertEquals("shū", ToStringUtil.getRomanization("シュウ"));
    assertEquals("she", ToStringUtil.getRomanization("シェ"));
    assertEquals("si", ToStringUtil.getRomanization("スィ"));
  }

  @Test
  public void testRomanizationTRow() {
    assertEquals("ta", ToStringUtil.getRomanization("タ"));
    assertEquals("chi", ToStringUtil.getRomanization("チ"));
    assertEquals("tsu", ToStringUtil.getRomanization("ツ"));
    assertEquals("te", ToStringUtil.getRomanization("テ"));
    assertEquals("to", ToStringUtil.getRomanization("ト"));
    assertEquals("tō", ToStringUtil.getRomanization("トウ"));
    assertEquals("cha", ToStringUtil.getRomanization("チャ"));
    assertEquals("chu", ToStringUtil.getRomanization("チュ"));
    assertEquals("cho", ToStringUtil.getRomanization("チョ"));
    assertEquals("chō", ToStringUtil.getRomanization("チョウ"));
    assertEquals("chū", ToStringUtil.getRomanization("チュウ"));
    assertEquals("che", ToStringUtil.getRomanization("チェ"));
    assertEquals("tsa", ToStringUtil.getRomanization("ツァ"));
    assertEquals("tsi", ToStringUtil.getRomanization("ツィ"));
    assertEquals("tse", ToStringUtil.getRomanization("ツェ"));
    assertEquals("tso", ToStringUtil.getRomanization("ツォ"));
    assertEquals("tsyu", ToStringUtil.getRomanization("ツュ"));
    assertEquals("ti", ToStringUtil.getRomanization("ティ"));
    assertEquals("tu", ToStringUtil.getRomanization("テゥ"));
    assertEquals("tyu", ToStringUtil.getRomanization("テュ"));
  }

  @Test
  public void testRomanizationNRow() {
    assertEquals("na", ToStringUtil.getRomanization("ナ"));
    assertEquals("ni", ToStringUtil.getRomanization("ニ"));
    assertEquals("nu", ToStringUtil.getRomanization("ヌ"));
    assertEquals("ne", ToStringUtil.getRomanization("ネ"));
    assertEquals("no", ToStringUtil.getRomanization("ノ"));
    assertEquals("nō", ToStringUtil.getRomanization("ノウ"));
    assertEquals("nya", ToStringUtil.getRomanization("ニャ"));
    assertEquals("nyu", ToStringUtil.getRomanization("ニュ"));
    assertEquals("nyo", ToStringUtil.getRomanization("ニョ"));
    assertEquals("nyō", ToStringUtil.getRomanization("ニョウ"));
    assertEquals("nyū", ToStringUtil.getRomanization("ニュウ"));
    assertEquals("nye", ToStringUtil.getRomanization("ニェ"));
  }

  @Test
  public void testRomanizationHRow() {
    assertEquals("ha", ToStringUtil.getRomanization("ハ"));
    assertEquals("hi", ToStringUtil.getRomanization("ヒ"));
    assertEquals("fu", ToStringUtil.getRomanization("フ"));
    assertEquals("he", ToStringUtil.getRomanization("ヘ"));
    assertEquals("ho", ToStringUtil.getRomanization("ホ"));
    assertEquals("hō", ToStringUtil.getRomanization("ホウ"));
    assertEquals("hu", ToStringUtil.getRomanization("ホゥ"));
    assertEquals("hya", ToStringUtil.getRomanization("ヒャ"));
    assertEquals("hyu", ToStringUtil.getRomanization("ヒュ"));
    assertEquals("hyo", ToStringUtil.getRomanization("ヒョ"));
    assertEquals("hyō", ToStringUtil.getRomanization("ヒョウ"));
    assertEquals("hyū", ToStringUtil.getRomanization("ヒュウ"));
    assertEquals("hye", ToStringUtil.getRomanization("ヒェ"));
    assertEquals("fa", ToStringUtil.getRomanization("ファ"));
    assertEquals("fi", ToStringUtil.getRomanization("フィ"));
    assertEquals("fe", ToStringUtil.getRomanization("フェ"));
    assertEquals("fo", ToStringUtil.getRomanization("フォ"));
    assertEquals("fya", ToStringUtil.getRomanization("フャ"));
    assertEquals("fyu", ToStringUtil.getRomanization("フュ"));
    assertEquals("fye", ToStringUtil.getRomanization("フィェ"));
    assertEquals("fyo", ToStringUtil.getRomanization("フョ"));
  }

  @Test
  public void testRomanizationMRow() {
    assertEquals("ma", ToStringUtil.getRomanization("マ"));
    assertEquals("mi", ToStringUtil.getRomanization("ミ"));
    assertEquals("mu", ToStringUtil.getRomanization("ム"));
    assertEquals("me", ToStringUtil.getRomanization("メ"));
    assertEquals("mo", ToStringUtil.getRomanization("モ"));
    assertEquals("mō", ToStringUtil.getRomanization("モウ"));
    assertEquals("mya", ToStringUtil.getRomanization("ミャ"));
    assertEquals("myu", ToStringUtil.getRomanization("ミュ"));
    assertEquals("myo", ToStringUtil.getRomanization("ミョ"));
    assertEquals("myō", ToStringUtil.getRomanization("ミョウ"));
    assertEquals("myū", ToStringUtil.getRomanization("ミュウ"));
    assertEquals("mye", ToStringUtil.getRomanization("ミェ"));
  }

  @Test
  public void testRomanizationYRW() {
    assertEquals("ya", ToStringUtil.getRomanization("ヤ"));
    assertEquals("yu", ToStringUtil.getRomanization("ユ"));
    assertEquals("yo", ToStringUtil.getRomanization("ヨ"));
    assertEquals("yō", ToStringUtil.getRomanization("ヨウ"));
    assertEquals("ya", ToStringUtil.getRomanization("ャ"));
    assertEquals("yu", ToStringUtil.getRomanization("ュ"));
    assertEquals("yo", ToStringUtil.getRomanization("ョ"));
    assertEquals("ra", ToStringUtil.getRomanization("ラ"));
    assertEquals("ri", ToStringUtil.getRomanization("リ"));
    assertEquals("ru", ToStringUtil.getRomanization("ル"));
    assertEquals("re", ToStringUtil.getRomanization("レ"));
    assertEquals("ro", ToStringUtil.getRomanization("ロ"));
    assertEquals("rō", ToStringUtil.getRomanization("ロウ"));
    assertEquals("rya", ToStringUtil.getRomanization("リャ"));
    assertEquals("ryu", ToStringUtil.getRomanization("リュ"));
    assertEquals("ryo", ToStringUtil.getRomanization("リョ"));
    assertEquals("ryō", ToStringUtil.getRomanization("リョウ"));
    assertEquals("ryū", ToStringUtil.getRomanization("リュウ"));
    assertEquals("rye", ToStringUtil.getRomanization("リェ"));
    assertEquals("wa", ToStringUtil.getRomanization("ワ"));
    assertEquals("wa", ToStringUtil.getRomanization("ヮ"));
    assertEquals("i", ToStringUtil.getRomanization("ヰ"));
    assertEquals("e", ToStringUtil.getRomanization("ヱ"));
    assertEquals("o", ToStringUtil.getRomanization("ヲ"));
  }

  @Test
  public void testRomanizationNAndDouble() {
    // ン before b/p/m row → m
    assertEquals("mba", ToStringUtil.getRomanization("ンバ"));
    assertEquals("mbi", ToStringUtil.getRomanization("ンビ"));
    assertEquals("mbu", ToStringUtil.getRomanization("ンブ"));
    assertEquals("mbe", ToStringUtil.getRomanization("ンベ"));
    assertEquals("mbo", ToStringUtil.getRomanization("ンボ"));
    assertEquals("mpa", ToStringUtil.getRomanization("ンパ"));
    assertEquals("mpi", ToStringUtil.getRomanization("ンピ"));
    assertEquals("mpu", ToStringUtil.getRomanization("ンプ"));
    assertEquals("mpe", ToStringUtil.getRomanization("ンペ"));
    assertEquals("mpo", ToStringUtil.getRomanization("ンポ"));
    assertEquals("mma", ToStringUtil.getRomanization("ンマ"));
    assertEquals("mmi", ToStringUtil.getRomanization("ンミ"));
    assertEquals("mmu", ToStringUtil.getRomanization("ンム"));
    assertEquals("mme", ToStringUtil.getRomanization("ンメ"));
    assertEquals("mmo", ToStringUtil.getRomanization("ンモ"));
    // ン before vowel/ya/yu/yo → n'
    assertEquals("n'a", ToStringUtil.getRomanization("ンア"));
    assertEquals("n'i", ToStringUtil.getRomanization("ンイ"));
    assertEquals("n'u", ToStringUtil.getRomanization("ンウ"));
    assertEquals("n'e", ToStringUtil.getRomanization("ンエ"));
    assertEquals("n'o", ToStringUtil.getRomanization("ンオ"));
    assertEquals("n'ya", ToStringUtil.getRomanization("ンヤ"));
    assertEquals("n'yu", ToStringUtil.getRomanization("ンユ"));
    assertEquals("n'yo", ToStringUtil.getRomanization("ンヨ"));
    // ン default → n
    assertEquals("nka", ToStringUtil.getRomanization("ンカ"));
    assertEquals("n", ToStringUtil.getRomanization("ン"));
    // ッ doubling
    assertEquals("kka", ToStringUtil.getRomanization("ッカ"));
    assertEquals("kki", ToStringUtil.getRomanization("ッキ"));
    assertEquals("kku", ToStringUtil.getRomanization("ック"));
    assertEquals("kke", ToStringUtil.getRomanization("ッケ"));
    assertEquals("kko", ToStringUtil.getRomanization("ッコ"));
    assertEquals("ssa", ToStringUtil.getRomanization("ッサ"));
    assertEquals("sshi", ToStringUtil.getRomanization("ッシ"));
    assertEquals("ssu", ToStringUtil.getRomanization("ッス"));
    assertEquals("sse", ToStringUtil.getRomanization("ッセ"));
    assertEquals("sso", ToStringUtil.getRomanization("ッソ"));
    assertEquals("tta", ToStringUtil.getRomanization("ッタ"));
    assertEquals("tchi", ToStringUtil.getRomanization("ッチ"));
    assertEquals("ttsu", ToStringUtil.getRomanization("ッツ"));
    assertEquals("tte", ToStringUtil.getRomanization("ッテ"));
    assertEquals("tto", ToStringUtil.getRomanization("ット"));
    assertEquals("ppa", ToStringUtil.getRomanization("ッパ"));
    assertEquals("ppi", ToStringUtil.getRomanization("ッピ"));
    assertEquals("ppu", ToStringUtil.getRomanization("ップ"));
    assertEquals("ppe", ToStringUtil.getRomanization("ッペ"));
    assertEquals("ppo", ToStringUtil.getRomanization("ッポ"));
    // ッ with unrecognized next → no doubling
    assertEquals("", ToStringUtil.getRomanization("ッ"));
  }

  @Test
  public void testRomanizationGRow() {
    assertEquals("ga", ToStringUtil.getRomanization("ガ"));
    assertEquals("gi", ToStringUtil.getRomanization("ギ"));
    assertEquals("gu", ToStringUtil.getRomanization("グ"));
    assertEquals("ge", ToStringUtil.getRomanization("ゲ"));
    assertEquals("go", ToStringUtil.getRomanization("ゴ"));
    assertEquals("gō", ToStringUtil.getRomanization("ゴウ"));
    assertEquals("gya", ToStringUtil.getRomanization("ギャ"));
    assertEquals("gyu", ToStringUtil.getRomanization("ギュ"));
    assertEquals("gyo", ToStringUtil.getRomanization("ギョ"));
    assertEquals("gyō", ToStringUtil.getRomanization("ギョウ"));
    assertEquals("gyū", ToStringUtil.getRomanization("ギュウ"));
    assertEquals("gye", ToStringUtil.getRomanization("ギェ"));
    assertEquals("gwa", ToStringUtil.getRomanization("グァ"));
    assertEquals("gwi", ToStringUtil.getRomanization("グィ"));
    assertEquals("gwe", ToStringUtil.getRomanization("グェ"));
    assertEquals("gwo", ToStringUtil.getRomanization("グォ"));
    assertEquals("gwa", ToStringUtil.getRomanization("グヮ"));
  }

  @Test
  public void testRomanizationZRow() {
    assertEquals("za", ToStringUtil.getRomanization("ザ"));
    assertEquals("ji", ToStringUtil.getRomanization("ジ"));
    assertEquals("zu", ToStringUtil.getRomanization("ズ"));
    assertEquals("ze", ToStringUtil.getRomanization("ゼ"));
    assertEquals("zo", ToStringUtil.getRomanization("ゾ"));
    assertEquals("zō", ToStringUtil.getRomanization("ゾウ"));
    assertEquals("ja", ToStringUtil.getRomanization("ジャ"));
    assertEquals("ju", ToStringUtil.getRomanization("ジュ"));
    assertEquals("jo", ToStringUtil.getRomanization("ジョ"));
    assertEquals("jō", ToStringUtil.getRomanization("ジョウ"));
    assertEquals("jū", ToStringUtil.getRomanization("ジュウ"));
    assertEquals("je", ToStringUtil.getRomanization("ジェ"));
    assertEquals("zi", ToStringUtil.getRomanization("ズィ"));
    assertEquals("ji", ToStringUtil.getRomanization("ヂ"));
    assertEquals("zu", ToStringUtil.getRomanization("ヅ"));
  }

  @Test
  public void testRomanizationDRow() {
    assertEquals("da", ToStringUtil.getRomanization("ダ"));
    assertEquals("de", ToStringUtil.getRomanization("デ"));
    assertEquals("do", ToStringUtil.getRomanization("ド"));
    assertEquals("dō", ToStringUtil.getRomanization("ドウ"));
    assertEquals("du", ToStringUtil.getRomanization("ドゥ"));
    assertEquals("di", ToStringUtil.getRomanization("ディ"));
    assertEquals("dyu", ToStringUtil.getRomanization("デュ"));
  }

  @Test
  public void testRomanizationBRow() {
    assertEquals("ba", ToStringUtil.getRomanization("バ"));
    assertEquals("bi", ToStringUtil.getRomanization("ビ"));
    assertEquals("bu", ToStringUtil.getRomanization("ブ"));
    assertEquals("be", ToStringUtil.getRomanization("ベ"));
    assertEquals("bo", ToStringUtil.getRomanization("ボ"));
    assertEquals("bō", ToStringUtil.getRomanization("ボウ"));
    assertEquals("bya", ToStringUtil.getRomanization("ビャ"));
    assertEquals("byu", ToStringUtil.getRomanization("ビュ"));
    assertEquals("byo", ToStringUtil.getRomanization("ビョ"));
    assertEquals("byō", ToStringUtil.getRomanization("ビョウ"));
    assertEquals("byū", ToStringUtil.getRomanization("ビュウ"));
    assertEquals("bye", ToStringUtil.getRomanization("ビェ"));
  }

  @Test
  public void testRomanizationPRow() {
    assertEquals("pa", ToStringUtil.getRomanization("パ"));
    assertEquals("pi", ToStringUtil.getRomanization("ピ"));
    assertEquals("pu", ToStringUtil.getRomanization("プ"));
    assertEquals("pe", ToStringUtil.getRomanization("ペ"));
    assertEquals("po", ToStringUtil.getRomanization("ポ"));
    assertEquals("pō", ToStringUtil.getRomanization("ポウ"));
    assertEquals("pya", ToStringUtil.getRomanization("ピャ"));
    assertEquals("pyu", ToStringUtil.getRomanization("ピュ"));
    assertEquals("pyo", ToStringUtil.getRomanization("ピョ"));
    assertEquals("pyō", ToStringUtil.getRomanization("ピョウ"));
    assertEquals("pyū", ToStringUtil.getRomanization("ピュウ"));
    assertEquals("pye", ToStringUtil.getRomanization("ピェ"));
  }

  @Test
  public void testRomanizationVAndMisc() {
    assertEquals("v", ToStringUtil.getRomanization("ヴ"));
    assertEquals("vye", ToStringUtil.getRomanization("ヴィェ"));
    // long vowel mark is suppressed
    assertEquals("", ToStringUtil.getRomanization("ー"));
    // non-katakana characters pass through (default case)
    assertEquals("A", ToStringUtil.getRomanization("A"));
    assertEquals("", ToStringUtil.getRomanization(""));
  }
}
