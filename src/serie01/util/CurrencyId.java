package serie01.util;

/**
 * Base de données concernant les monnaies du monde.
 * Cette BdD est complète (à ma connaissance, au 31/12/2001) et provient de
 *  plusieurs documents trouvés sur le Ouèbe :
 * <ul>
 * <li>Codes ISO : http://www.oanda.com/site/help/iso_code.shtml</li>
 * <li>Taux de change donné au BULLETIN OFFICIEL DES IMPÔTS, DIRECTION GÉNÉRALE
 *  DES IMPÔTS, 5 F-14-02, N° 62 du 29 MARS 2002 :
 *  http://www11.minefi.gouv.fr/boi/boi2002/5fppub/textes/5f1402/5f1402.htm</li>
 * </ul>
 * <p>
 * <i>Nota : des informations plus précises peuvent être trouvées sur wikipedia
 *  (http://fr.wikipedia.org/wiki/Codes_des_monnaies).</i>
 * </p>
 */
public enum CurrencyId {
    AED(3.23105, "AED", "Dirham", "Emirats arabes unis"),
    AFA(4158.2, "AFA", "Afghani", "Afghanistan"),
    ALL(121.808, "ALL", "Lek", "Albanie"),
    AMD(494.879, "AMD", "Dram", "Arménie"),
    ANG(1.56587, "ANG", "Florin des Antilles néerlandaises",
            "Antilles néerlandaises"),
    AON(27.514, "AON", "Nouveau Kwanza", "Angola"),
    ARS(1.38805, "ARS", "Peso argentin", "Argentine"),
    ATS(13.7603, "ATS", "Schilling", "Autriche"),
    AUD(1.728, "AUD", "Dollar australien", "Australie et Océanie"),
    AWG(1.56587, "AWG", "Florin d'Aruba", "Aruba"),
    AZM(4200.17, "AZM", "Manat azerbaïdjanais", "Azerbaïdjan"),
    BAM(1.93926, "BAM", "Mark convertible", "Bosnie-Herzégovine"),
    BBD(1.7552, "BBD", "Dollar de la Barbade", "Barbade"),
    BDT(50.3686, "BDT", "Taka", "Bangladesch"),
    BEF(40.3399, "BEF", "Franc belge", "Belgique"),
    BGL(1.9463, "BGL", "Lev", "Bulgarie"),
    BHD(0.33167, "BHD", "Dinar bahreïni", "Bahreïn"),
    BIF(748.173, "BIF", "Franc du Burundi", "Burundi"),
    BMD(0.8813, "BMD", "Dollar des Bermudes", "Bermudes"),
    BND(1.62609, "BND", "Dollar de Brunei", "Brunei Darussalam"),
    BOB(5.99144, "BOB", "Boliviano", "Bolivie"),
    BRL(2.04246, "BRL", "Réal", "Brésil"),
    BSD(0.8813, "BSD", "Dollar des Bahamas", "Bahamas"),
    BTN(42.4679, "BTN", "Ngultrum", "Bhoutan"),
    BWP(6.08858, "BWP", "Pula", "Botswana"),
    BYB(1390.08, "BYB", "Rouble biélorusse", "Biélorussie (Belarus)"),
    BZD(1.7596, "BZD", "Dollar de Belize", "Belize"),
    CAD(1.4077, "CAD", "Dollar canadien", "Canada"),
    CDF(280.096, "CDF", "Franc congolais", "Congo, République démocratique"),
    CHF(1.4829, "CHF", "Franc suisse", "Suisse, Liechtenstein"),
    CLP(574.312, "CLP", "Peso chilien", "Chili"),
    CNY(7.28114, "CNY", "Renminbi Yuan", "Chine"),
    COP(2060.26, "COP", "Peso colombien", "Colombie"),
    CRC(300.022, "CRC", "Colon", "Costa Rica"),
    CUP(0.8813, "CUP", "Peso cubain", "Cuba"),
    CVE(105.309, "CVE", "Escudo du Cap-Vert", "Cap-Vert"),
    CYP(0.57504, "CYP", "Livre cypriote", "Chypre"),
    CZK(31.962, "CZK", "Couronne tchèque", "République tchèque"),
    DEM(1.95583, "DEM", "Mark allemand", "Allemagne"),
    DJF(149.443, "DJF", "Franc de Djibouti", "Djibouti"),
    DKK(7.4365, "DKK", "Couronne danoise", "Danemark, Féroé, Groenland"),
    DOP(14.2731, "DOP", "Peso dominicain", "République dominicaine"),
    DZD(67.9294, "DZD", "Dinar algérien", "Algérie"),
    ECS(0.8813, "ECS", "Sucre", "Equateur"),
    EEK(15.6466, "EEK", "Couronne estonienne", "Estonie"),
    EGP(4.02903, "EGP", "Livre égyptienne", "Egypte"),
    ERN(8.35909, "ERN", "Nafka", "Erythrée"),
    ESP(166.386, "ESP", "Peseta", "Espagne, Andorre"),
    ETB(7.43786, "ETB", "Birr", "Ethiopie"),
    EUR(1.0, "EUR", "Euro", "UEM (dès le 1.1.1999)"),
    FIM(5.94573, "FIM", "Mark finlandais", "Finlande"),
    FJD(2.0293, "FJD", "Dollar fidjien", "Fidji"),
    FKP(0.4039, "FKP", "Livre des îles Falkland", "Îles Malouines (Falkland)"),
    FRF(6.55957, "FRF", "Franc français",
            "France, Andorre, Monaco, France d'outre-mer"),
    GBP(0.6085, "GBP", "Livre sterling", "Grande-Bretagne"),
    GEL(1.82098, "GEL", "Lari", "Géorgie"),
    GHC(6509.78, "GHC", "Cedi", "Ghana"),
    GIP(0.6085, "GIP", "Livre de Gibraltar", "Gibraltar"),
    GMD(15.1748, "GMD", "Dalasi", "Gambie"),
    GNF(1731.6, "GNF", "Franc guinéen", "Guinée"),
    GRD(340.75, "GRD", "Drachme", "Grèce"),
    GTQ(6.9411, "GTQ", "Quetzal", "Guatemala"),
    GYD(158.786, "GYD", "Dollar de Guyana", "Guyana"),
    HKD(6.8723, "HKD", "Dollar de Hong-Kong", "Hong-Kong, Zone administrative"),
    HNL(13.9001, "HNL", "Lempira", "Honduras"),
    HRK(7.28761, "HRK", "Kuna", "Croatie"),
    HTG(23.0385, "HTG", "Gourde", "Haïti"),
    HUF(245.18, "HUF", "Forint", "Hongrie"),
    IDR(9214.86, "IDR", "Rupiah", "Indonésie"),
    IEP(0.787564, "IEP", "Livre irlandaise", "Irlande"),
    ILS(3.8698, "ILS", "Nouveau shekel", "Israël"),
    INR(42.4631, "INR", "Roupie indienne", "Inde"),
    IQD(0.27667, "IQD", "Dinar irakien", "Irak"),
    IRR(1541.67, "IRR", "Rial", "Iran, République islamique"),
    ISK(91.48, "ISK", "Couronne islandaise", "Islande"),
    ITL(1936.27, "ITL", "Lire", "Italie, Saint-Marin, Vatican"),
    JMD(42.3576, "JMD", "Dollar de la Jamaïque", "Jamaïque"),
    JOD(0.62353, "JOD", "Dinar jordanien", "Jordanie"),
    JPY(115.33, "JPY", "Yen", "Japon"),
    KES(68.9685, "KES", "Shilling du Kenya", "Kenya"),
    KGS(42.2124, "KGS", "Som du Kirghizistan", "Kirghizistan"),
    KHR(3387.23, "KHR", "Riel", "Cambodge"),
    KMF(491.96775, "KMF", "Franc comorien", "Comores"),
    KPW(1161.55, "KPW", "Won (Corée du sud)",
            "Corée, République populaire démocratique"),
    KRW(1.93534, "KRW", "Won (Corée du nord)", "Corée, République"),
    KWD(0.27033, "KWD", "Dinar koweïtien", "Koweït"),
    KYD(0.70551, "KYD", "Dollar des îles Caïmans", "Îles Caïmans"),
    KZT(132.131, "KZT", "Tenge", "Kazakhstan"),
    LAK(6680.53, "LAK", "Kip", "Laos"),
    LBP(1330.99, "LBP", "Livre libanaise", "Liban"),
    LKR(81.8121, "LKR", "Roupie de Sri-Lanka", "Sri Lanka (Ceylan)"),
    LRD(39.6585, "LRD", "Dollar libérien", "Libéria"),
    LSL(10.5168, "LSL", "Loti", "Lesotho"),
    LTL(3.5228, "LTL", "Litas", "Lituanie"),
    LUF(40.3399, "LUF", "Franc luxembourgeois", "Luxembourg"),
    LVL(0.5563, "LVL", "Lats", "Lettonie"),
    LYD(0.56398, "LYD", "Dinar libyen", "Libye, Jamahirija arabe populaire"),
    MAD(10.223, "MAD", "Dirham", "Maroc"),
    MDL(11.5179, "MDL", "Leu de Moldavie", "Moldavie, République"),
    MGF(5682.86, "MGF", "Franc malgache", "Madagascar"),
    MKD(60.109, "MKD", "Denar", "Macédoine"),
    MMK(5.71805, "MMK", "Kyat", "Myanmar (Birmanie)"),
    MNT(967.67, "MNT", "Tughrik", "Mongolie"),
    MOP(7.06509, "MOP", "Pataca", "Macao"),
    MRO(232.241, "MRO", "Ouguiya", "Mauritanie"),
    MTL(0.3994, "MTL", "Livre maltaise", "Malte"),
    MUR(26.6637, "MUR", "Roupie mauricienne", "Maurice"),
    MVR(10.3541, "MVR", "Rufiyaa", "Maldives"),
    MWK(58.5264, "MWK", "Kwacha du Malawi", "Malawi"),
    MXN(8.04059, "MXN", "Peso mexicain", "Mexique"),
    MYR(3.34286, "MYR", "Ringgit", "Malaysia"),
    MZM(19353.4, "MZM", "Metical", "Mozambique"),
    NAD(10.5168, "NAD", "Dollar namibien", "Namibie"),
    NGN(102.925, "NGN", "Naira", "Nigeria"),
    NIO(12.1047, "NIO", "Cordoba", "Nicaragua"),
    NLG(2.20371, "NLG", "Florin", "Pays-Bas"),
    NOK(7.9515, "NOK", "Couronne norvégienne", "Norvège, Svalbard"),
    NPR(66.9276, "NPR", "Roupie népalaise", "Népal"),
    NZD(2.1215, "NZD", "Dollar néo-zélandais", "Nouvelle-Zélande, Océanie"),
    OMR(0.32858, "OMR", "Riyal omanais", "Oman"),
    PAB(0.8813, "PAB", "Balboa", "Panama"),
    PEN(3.02837, "PEN", "Nouveau sol", "Pérou"),
    PGK(3.27026, "PGK", "Kina", "Papouasie-Nouvelle-Guinée"),
    PHP(45.5245, "PHP", "Peso philippin", "Philippines"),
    PKR(52.826, "PKR", "Roupie pakistanaise", "Pakistan"),
    PLN(3.4953, "PLN", "Zloty", "Pologne"),
    PTE(200.482, "PTE", "Escudo", "Portugal"),
    PYG(4077.41, "PYG", "Guarani", "Paraguay"),
    QAR(3.20176, "QAR", "Riyal qatari", "Qatar"),
    ROL(27817.0, "ROL", "Leu", "Roumanie"),
    RUB(26.8352, "RUB", "Rouble", "Fédération de Russie"),
    RWF(396.384, "RWF", "Franc rwandais", "Rwanda"),
    SAR(3.29923, "SAR", "Riyal saoudien", "Arabie Saoudite"),
    SBD(4.95324, "SBD", "Dollar des îles Salomon", "Salomon"),
    SCR(5.04948, "SCR", "Roupie seychelloise", "Seychelles"),
    SDD(226.444, "SDD", "Dinar soudanais", "Soudan"),
    SEK(9.3012, "SEK", "Couronne suédoise", "Suède"),
    SGD(1.6306, "SGD", "Dollar de Singapour", "Singapour"),
    SHP(0.6085, "SHP", "Livre de Sainte-Hélène", "Sainte-Hélène"),
    SIT(218.8364, "SIT", "Tolar", "Slovénie"),
    SKK(42.78, "SKK", "Couronne slovaque", "Slovaquie"),
    SLL(1737.41, "SLL", "Leone", "Sierra Leone"),
    SOS(2293.29, "SOS", "Shilling somali", "Somalie"),
    SRG(1921.09, "SRG", "Guinée du Surinam", "Surinam"),
    STD(7822.73, "STD", "Dobra", "Sao Tomé et Principe"),
    SVC(7.69737, "SVC", "Colon", "Salvador"),
    SYP(43.0754, "SYP", "Livre syrienne", "Syrie"),
    SZL(10.5168, "SZL", "Lilangeni", "Swaziland"),
    THB(38.8915, "THB", "Baht", "Thaïlande"),
    TJR(2.24323, "TJR", "Somoni", "Tadjikistan"),
    TMM(4574.44, "TMM", "Manat du Turkménistan", "Turkménistan"),
    TND(1.29479, "TND", "Dinar tunisien", "Tunisie"),
    TOP(1.92408, "TOP", "Pa'anga", "Tonga"),
    TRL(1269500.0, "TRL", "Livre turque", "Turquie"),
    TTD(5.38376, "TTD", "Dollar de Trinidad et Tobago", "Trinidad et Tobago"),
    TWD(30.8335, "TWD", "Nouveau dollar de Taiwan", "Chine (Taiwan)"),
    TZS(806.245, "TZS", "Shilling tanzanien", "Tanzanie"),
    UAH(4.65643, "UAH", "Grivna", "Ukraine"),
    UGX(1521.88, "UGX", "Schilling ougandais", "Ouganda"),
    USD(0.8813, "USD", "Dollar US",
            "Etats-Unis, Îles Marshall, Panama, Porto Rico"),
    UYU(12.2243, "UYU", "Peso uruguayen", "Uruguay"),
    UZS(604.266, "UZS", "Sum", "Ouzbékistan"),
    VEB(671.739, "VEB", "Bolivar", "Vénézuela"),
    VND(13249.6, "VND", "Dong", "Viêtnam"),
    VUV(129.58, "VUV", "Vatu", "Vanuatu"),
    WST(3.13954, "WST", "Tala", "Samoa-Occidentales"),
    XAF(655.957, "XAF", "Franc CFA BEAC",
            "Guinée équatoriale, Gabon, Cameroun, Congo (Brazzaville),"
            + " Tchad, République centrafricaine"),
    XCD(2.37519, "XCD", "Dollar des Caraïbes de l'Est",
            "Anguilla, Antigua-et-Barbuda, Dominique, Grenade, Montserrat,"
            + " Saint-Kitts-et-Nevis, Sainte-Lucie,"
            + " Saint-Vincent-et-les Grenadines"),
    XOF(655.957, "XOF", "Franc CFA BCEAO",
            "Bénin, Burkina Faso, Côte-d'Ivoire, Guinée-Bissau, Mali,"
            + " Niger, Sénégal, Togo"),
    XPF(119.3317422, "XPF", "Franc CFP",
            "Polynésie française, Nouvelle-Calédonie, Wallis-et-Futuna"),
    YER(149.461, "YER", "Riyal yéménite", "Yémen"),
    YUM(58.1103, "YUM", "Nouveau dinar yougoslave",
            "Yougoslavie (Serbie - Monténégro)"),
    ZAR(10.4302, "ZAR", "Rand", "Afrique du Sud, Lesotho, Namibie"),
    ZMK(3551.79, "ZMK", "Kwacha", "Zambie"),
    ZRN(280.096, "ZRN", "Franc congolais", "Congo, République démocratique"),
    ZWD(48.3835, "ZWD", "Dollar de Zimbabwe", "Zimbabwe");

    private final double rate;
    private final String isoCode;
    private final String name;
    private final String land;
    
    CurrencyId(double rate, String isoCode, String name, String land) {
        this.rate = rate;
        this.isoCode = isoCode;
        this.name = name;
        this.land = land;
    }

    double getRateForYear2001() {
        return rate;
    }

    String getIsoCode() {
        return isoCode;
    }

    String getName() {
        return name;
    }

    String getLand() {
        return land;
    }
}
