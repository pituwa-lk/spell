'use strict';

var fs = require('fs');

fs.writeFile('/var/tmp/read.in', transform(fs.readFileSync('/var/tmp/send.out', {"encoding" : "utf8"})));

function transform(text) {

    text = text.replace(/'/g, "Ã");
    text = text.replace(/"/g, "»");
    text = text.replace(/›/g, "ශ්‍රී");
    text = text.replace(/ff;%/g, "ත්‍රෛ");
    text = text.replace(/ffY/g, "ශෛ");
    text = text.replace(/ffp/g, "චෛ");
    text = text.replace(/ffc/g, "ජෛ");
    text = text.replace(/ffk/g, "නෛ");
    text = text.replace(/ffl/g, "කෛ");
    text = text.replace(/ffu/g, "මෛ");
    text = text.replace(/ffm/g, "පෛ");
    text = text.replace(/ffo/g, "දෛ");
    text = text.replace(/ff;/g, "තෛ");
    text = text.replace(/ffO/g, "ධෛ");
    text = text.replace(/ffj/g, "වෛ");
    text = text.replace(/fm%!/g, "ප්‍රෞ");
    text = text.replace(/fIHda/g, "ෂ්‍යෝ");
    text = text.replace(/fPHda/g, "ඡ්‍යෝ");
    text = text.replace(/fVHda/g, "ඪ්‍යෝ");
    text = text.replace(/f>Hda/g, "ඝ්‍යෝ");
    text = text.replace(/fLHda/g, "ඛ්‍යෝ");
    text = text.replace(/f<Hda/g, "ළ්‍යෝ");
    text = text.replace(/fMHda/g, "ඵ්‍යෝ");
    text = text.replace(/fGHda/g, "ඨ්‍යෝ");
    text = text.replace(/fYHda/g, "ශ්‍යෝ");
    text = text.replace(/fCIHda/g, "ක්‍ෂ්‍යෝ");
    text = text.replace(/fnHda/g, "බ්‍යෝ");
    text = text.replace(/fpHda/g, "ච්‍යෝ");
    text = text.replace(/fvHda/g, "ඩ්‍යෝ");
    text = text.replace(/f\*Hda/g, "ෆ්‍යෝ");
    text = text.replace(/f\.Hda/g, "ග්‍යෝ");
    text = text.replace(/fcHda/g, "ජ්‍යෝ");
    text = text.replace(/flHda/g, "ක්‍යෝ");
    text = text.replace(/f,Hda/g, "ල්‍යෝ");
    text = text.replace(/fuHda/g, "ම්‍යෝ");
    text = text.replace(/fkHda/g, "න්‍යෝ");
    text = text.replace(/fmHda/g, "ප්‍යෝ");
    text = text.replace(/foHda/g, "ද්‍යෝ");
    text = text.replace(/fiHda/g, "ස්‍යෝ");
    text = text.replace(/fgHda/g, "ට්‍යෝ");
    text = text.replace(/fjHda/g, "ව්‍යෝ");
    text = text.replace(/f;Hda/g, "ත්‍යෝ");
    text = text.replace(/fNHda/g, "භ්‍යෝ");
    text = text.replace(/fOHda/g, "ධ්‍යෝ");
    text = text.replace(/f:Hda/g, "ථ්‍යෝ");
    text = text.replace(/fIHd/g, "ෂ්‍යො");
    text = text.replace(/fYHd/g, "ශ්‍යො");
    text = text.replace(/fLHd/g, "ඛ්‍යො");
    text = text.replace(/fCIHd/g, "ක්‍ෂ්‍යො");
    text = text.replace(/fnHd/g, "බ්‍යො");
    text = text.replace(/fjHd/g, "ව්‍යො");
    text = text.replace(/fvHd/g, "ඩ්‍යො");
    text = text.replace(/f\*Hd/g, "ෆ්‍යො");
    text = text.replace(/f\.Hd/g, "ග්‍යො");
    text = text.replace(/fcHd/g, "ජ්‍යො");
    text = text.replace(/flHd/g, "ක්‍යො");
    text = text.replace(/fuHd/g, "ම්‍යො");
    text = text.replace(/fmHd/g, "ප්‍යො");
    text = text.replace(/foHd/g, "ද්‍යො");
    text = text.replace(/fiHd/g, "ස්‍යො");
    text = text.replace(/fgHd/g, "ට්‍යො");
    text = text.replace(/fjHd/g, "ව්‍යො");
    text = text.replace(/f;Hd/g, "ත්‍යො");
    text = text.replace(/fNHd/g, "භ්‍යො");
    text = text.replace(/fOHd/g, "ධ්‍යො");
    text = text.replace(/f:Hd/g, "ථ්‍යො");
    text = text.replace(/fIH/g, "ෂ්‍යෙ");
    text = text.replace(/fPH/g, "ඡ්‍යෙ");
    text = text.replace(/f<H/g, "ළ්‍යෙ");
    text = text.replace(/fKH/g, "ණ්‍යෙ");
    text = text.replace(/fpH/g, "ච්‍යෙ");
    text = text.replace(/f,H/g, "ල්‍යෙ");
    text = text.replace(/fkH/g, "න්‍යෙ");
    text = text.replace(/fYH/g, "ශ්‍යෙ");
    text = text.replace(/fLH/g, "ඛ්‍යෙ");
    text = text.replace(/fCIH/g, "ක්‍ෂ්‍යෙ");
    text = text.replace(/fnH/g, "බ්‍යෙ");
    text = text.replace(/fvH/g, "ඩ්‍යෙ");
    text = text.replace(/f\*H/g, "ෆ්‍යෙ");
    text = text.replace(/f\.H/g, "ග්‍යෙ");
    text = text.replace(/fcH/g, "ජ්‍යෙ");
    text = text.replace(/flH/g, "ක්‍යෙ");
    text = text.replace(/fuH/g, "ම්‍යෙ");
    text = text.replace(/fmH/g, "ප්‍යෙ");
    text = text.replace(/foH/g, "ද්‍යෙ");
    text = text.replace(/fiH/g, "ස්‍යෙ");
    text = text.replace(/fgH/g, "ට්‍යෙ");
    text = text.replace(/fjH/g, "ව්‍යෙ");
    text = text.replace(/f;H/g, "ත්‍යෙ");
    text = text.replace(/fNH/g, "භ්‍යෙ");
    text = text.replace(/fOH/g, "ධ්‍යෙ");
    text = text.replace(/f:H/g, "ථ්‍යෙ");
    text = text.replace(/hH_/g, "ර්ය");
    text = text.replace(/fI%da/g, "ෂ්‍රෝ");
    text = text.replace(/f>%da/g, "‍ඝ්‍රෝ");
    text = text.replace(/fY%da/g, "ශ්‍රෝ");
    text = text.replace(/fCI%da/g, "ක්‍ෂ්‍රෝ");
    text = text.replace(/fn%da/g, "බ්‍රෝ");
    text = text.replace(/fv%da/g, "ඩ්‍රෝ");
    text = text.replace(/f\*%da/g, "ෆ්‍රෝ");
    text = text.replace(/f\.%da/g, "ග්‍රෝ");
    text = text.replace(/fl%da/g, "ක්‍රෝ");
    text = text.replace(/fm%da/g, "ප්‍රෝ");
    text = text.replace(/føda/g, "ද්‍රෝ");
    text = text.replace(/fi%da/g, "ස්‍රෝ");
    text = text.replace(/fg%da/g, "ට්‍රෝ");
    text = text.replace(/f\;%da/g, "ත්‍රෝ");
    text = text.replace(/fY%d/g, "ශ්‍රො");
    text = text.replace(/fv%d/g, "ඩ්‍රො");
    text = text.replace(/f\*%d/g, "ෆ්‍රො");
    text = text.replace(/f\.%d/g, "ග්‍රො");
    text = text.replace(/fl%d/g, "ක්‍රො");
    text = text.replace(/fm%d/g, "ප්‍රො");
    text = text.replace(/fød/g, "ද්‍රො");
    text = text.replace(/fi%d/g, "ස්‍රො");
    text = text.replace(/fg%d/g, "ට්‍රො");
    text = text.replace(/f\;%d/g, "ත්‍රො");
    text = text.replace(/%a/g, "a%");
    text = text.replace(/fYa%/g, "ශ්‍රේ");
    text = text.replace(/fí%/g, "බ්‍රේ");
    text = text.replace(/fâ%/g, "ඩ්‍රේ");
    text = text.replace(/f\*%a/g, "ෆ්‍රේ");
    text = text.replace(/f\.%a/g, "ග්‍රේ");
    text = text.replace(/fl%a/g, "ක්‍රේ");
    text = text.replace(/fm%a/g, "ප්‍රේ");
    text = text.replace(/føa/g, "ද්‍රේ");
    text = text.replace(/fia%/g, "ස්‍රේ");
    text = text.replace(/f\;a%/g, "ත්‍රේ");
    text = text.replace(/fè%/g, "ධ්‍රේ");
    text = text.replace(/fI%/g, "ෂ්‍රෙ");
    text = text.replace(/fY%/g, "ශ්‍රෙ");
    text = text.replace(/fn%/g, "බ්‍රෙ");
    text = text.replace(/f\*%/g, "ෆ්‍රෙ");
    text = text.replace(/f\.%/g, "ග්‍රෙ");
    text = text.replace(/fl%/g, "ක්‍රෙ");
    text = text.replace(/fm%/g, "ප්‍රෙ");
    text = text.replace(/fø/g, "ද්‍රෙ");
    text = text.replace(/fi%/g, "ස්‍රෙ");
    text = text.replace(/f\;%/g, "ත්‍රෙ");
    text = text.replace(/fN%/g, "භ්‍රෙ");
    text = text.replace(/fO%/g, "ධ්‍රෙ");
    text = text.replace(/fI!/g, "ෂෞ");
    text = text.replace(/fP!/g, "ඡෞ");
    text = text.replace(/fY!/g, "ශෞ");
    text = text.replace(/fn!/g, "බෞ");
    text = text.replace(/fp!/g, "චෞ");
    text = text.replace(/fv!/g, "ඩෞ");
    text = text.replace(/f\*!/g, "ෆෞ");
    text = text.replace(/f\.!/g, "ගෞ");
    text = text.replace(/fc!/g, "ජෞ");
    text = text.replace(/fl!/g, "කෞ");
    text = text.replace(/f,!/g, "ලෞ");
    text = text.replace(/fu!/g, "මෞ");
    text = text.replace(/fk!/g, "නෞ");
    text = text.replace(/fm!/g, "පෞ");
    text = text.replace(/fo!/g, "දෞ");
    text = text.replace(/fr!/g, "රෞ");
    text = text.replace(/fi!/g, "සෞ");
    text = text.replace(/fg!/g, "ටෞ");
    text = text.replace(/f\;!/g, "තෞ");
    text = text.replace(/fN!/g, "භෞ");
    text = text.replace(/f\[!/g, "ඤෞ");
    text = text.replace(/fIda/g, "ෂෝ");
    text = text.replace(/fUda/g, "ඹෝ");
    text = text.replace(/fPda/g, "ඡෝ");
    text = text.replace(/fVda/g, "ඪෝ");
    text = text.replace(/f>da/g, "ඝෝ");
    text = text.replace(/fLda/g, "ඛෝ");
    text = text.replace(/f<da/g, "ළෝ");
    text = text.replace(/f`yda/g, "ඟෝ");
    text = text.replace(/fKda/g, "ණෝ");
    text = text.replace(/fMda/g, "ඵෝ");
    text = text.replace(/fGda/g, "ඨෝ");
    text = text.replace(/f~da/g, "ඬෝ");
    text = text.replace(/fYda/g, "ශෝ");
    text = text.replace(/f\\{da/g, "ඥෝ");
    text = text.replace(/f\da/g, "ඳෝ");
    text = text.replace(/fIda/g, "ෂෝ");
    text = text.replace(/fnda/g, "බෝ");
    text = text.replace(/fpda/g, "චෝ");
    text = text.replace(/fvda/g, "ඩෝ");
    text = text.replace(/f\*da/g, "ෆෝ");
    text = text.replace(/f\.da/g, "ගෝ");
    text = text.replace(/fyda/g, "හෝ");
    text = text.replace(/fcda/g, "ජෝ");
    text = text.replace(/flda/g, "කෝ");
    text = text.replace(/f,da/g, "ලෝ");
    text = text.replace(/fuda/g, "මෝ");
    text = text.replace(/fkda/g, "නෝ");
    text = text.replace(/fmda/g, "පෝ");
    text = text.replace(/foda/g, "දෝ");
    text = text.replace(/frda/g, "රෝ");
    text = text.replace(/fida/g, "සෝ");
    text = text.replace(/fgda/g, "ටෝ");
    text = text.replace(/fjda/g, "වෝ");
    text = text.replace(/f\;da/g, "තෝ");
    text = text.replace(/fNda/g, "භෝ");
    text = text.replace(/fhda/g, "යෝ");
    text = text.replace(/f\[da/g, "ඤෝ");
    text = text.replace(/fOda/g, "ධෝ");
    text = text.replace(/f\:da/g, "ථෝ");
    text = text.replace(/fId/g, "ෂො");
    text = text.replace(/fUd/g, "ඹො");
    text = text.replace(/fPd/g, "ඡො");
    text = text.replace(/fVd/g, "ඪො");
    text = text.replace(/f>d/g, "ඝො");
    text = text.replace(/fLd/g, "ඛො");
    text = text.replace(/f<d/g, "ළො");
    text = text.replace(/f`yd/g, "ඟො");
    text = text.replace(/fKd/g, "ණො");
    text = text.replace(/fMd/g, "ඵො");
    text = text.replace(/fGd/g, "ඨො");
    text = text.replace(/f`Vd/g, "ඬො");
    text = text.replace(/fYd/g, "ශො");
    text = text.replace(/f\\{d/g, "ඥො");
    text = text.replace(/f\d/g, "ඳො");
    text = text.replace(/fId/g, "ෂො");
    text = text.replace(/fnd/g, "බො");
    text = text.replace(/fpd/g, "චො");
    text = text.replace(/fvd/g, "ඩො");
    text = text.replace(/f\*d/g, "ෆො");
    text = text.replace(/f\.d/g, "ගො");
    text = text.replace(/fyd/g, "හො");
    text = text.replace(/fcd/g, "ජො");
    text = text.replace(/fld/g, "කො");
    text = text.replace(/f,d/g, "ලො");
    text = text.replace(/fud/g, "මො");
    text = text.replace(/fkd/g, "නො");
    text = text.replace(/fmd/g, "පො");
    text = text.replace(/fod/g, "දො");
    text = text.replace(/frd/g, "රො");
    text = text.replace(/fid/g, "සො");
    text = text.replace(/fgd/g, "ටො");
    text = text.replace(/fjd/g, "වො");
    text = text.replace(/f\;d/g, "තො");
    text = text.replace(/fNd/g, "භො");
    text = text.replace(/fhd/g, "යො");
    text = text.replace(/f\[d/g, "ඤො");
    text = text.replace(/fOd/g, "ධො");
    text = text.replace(/f\:d/g, "ථො");
    text = text.replace(/fIa/g, "ෂේ");
    text = text.replace(/fò/g, "ඹේ");
    text = text.replace(/fPa/g, "ඡේ");
    text = text.replace(/fVa/g, "ඪේ");
    text = text.replace(/f>a/g, "ඝේ");
    text = text.replace(/fÄ/g, "ඛේ");
    text = text.replace(/f<a/g, "ළේ");
    text = text.replace(/f`Na/g, "ඟේ");
    text = text.replace(/fKa/g, "ණේ");
    text = text.replace(/fMa/g, "ඵේ");
    text = text.replace(/fGa/g, "ඨේ");
    text = text.replace(/fâ/g, "ඬේ");
    text = text.replace(/fYa/g, "ශේ");
    text = text.replace(/f\{a/g, "ඥේ");
    text = text.replace(/f\|a/g, "ඳේ");
    text = text.replace(/fËa/g, "ක්‍ෂේ");
    text = text.replace(/fí/g, "බේ");
    text = text.replace(/fÉ/g, "චේ");
    text = text.replace(/fâ/g, "ඩේ");
    text = text.replace(/f\*a/g, "ෆේ");
    text = text.replace(/f\.a/g, "ගේ");
    text = text.replace(/fya/g, "හේ");
    text = text.replace(/fca/g, "ජේ");
    text = text.replace(/fla/g, "කේ");
    text = text.replace(/f,a/g, "ලේ");
    text = text.replace(/fï/g, "මේ");
    text = text.replace(/fka/g, "නේ");
    text = text.replace(/fma/g, "පේ");
    text = text.replace(/foa/g, "දේ");
    text = text.replace(/f¾/g, "රේ");
    text = text.replace(/fia/g, "සේ");
    text = text.replace(/fÜ/g, "ටේ");
    text = text.replace(/fõ/g, "වේ");
    text = text.replace(/f\;a/g, "තේ");
    text = text.replace(/fNa/g, "භේ");
    text = text.replace(/fha/g, "යේ");
    text = text.replace(/f\[a/g, "ඤේ");
    text = text.replace(/fè/g, "ධේ");
    text = text.replace(/f\:a/g, "ථේ");
    text = text.replace(/ta/g, "ඒ");
    text = text.replace(/fÊ/g, "ජේ");
    text = text.replace(/wE/g, "ඈ");
    text = text.replace(/fI/g, "ෂෙ");
    text = text.replace(/fU/g, "ඹෙ");
    text = text.replace(/ft/g, "ඓ");
    text = text.replace(/fP/g, "ඡෙ");
    text = text.replace(/fV/g, "ඪෙ");
    text = text.replace(/f>/g, "ඝෙ");
    text = text.replace(/fL/g, "ඛෙ");
    text = text.replace(/f</g, "ළෙ");
    text = text.replace(/f`y/g, "ඟෙ");
    text = text.replace(/fK/g, "ණෙ");
    text = text.replace(/fM/g, "ඵෙ");
    text = text.replace(/fG/g, "ඨෙ");
    text = text.replace(/f~/g, "ඬෙ");
    text = text.replace(/fY/g, "ශෙ");
    text = text.replace(/f\{/g, "ඥෙ");
    text = text.replace(/f\|/g, "ඳෙ");
    text = text.replace(/fCI/g, "ක්‍ෂෙ");
    text = text.replace(/fn/g, "බෙ");
    text = text.replace(/fp/g, "චෙ");
    text = text.replace(/fv/g, "ඩෙ");
    text = text.replace(/f\*/g, "ෆෙ");
    text = text.replace(/f\./g, "ගෙ");
    text = text.replace(/fy/g, "හෙ");
    text = text.replace(/fc/g, "ජෙ");
    text = text.replace(/fl/g, "කෙ");
    text = text.replace(/f,/g, "ලෙ");
    text = text.replace(/fu/g, "මෙ");
    text = text.replace(/fk/g, "නෙ");
    text = text.replace(/fm/g, "පෙ");
    text = text.replace(/fo/g, "දෙ");
    text = text.replace(/fr/g, "රෙ");
    text = text.replace(/fi/g, "සෙ");
    text = text.replace(/fg/g, "ටෙ");
    text = text.replace(/fj/g, "වෙ");
    text = text.replace(/f\;/g, "තෙ");
    text = text.replace(/fN/g, "භෙ");
    text = text.replace(/fh/g, "යෙ");
    text = text.replace(/f\[/g, "ඤෙ");
    text = text.replace(/fO/g, "ධෙ");
    text = text.replace(/f\:/g, "ථෙ");
    text = text.replace(/IDD/g, "ෂෲ");
    text = text.replace(/YDD/g, "ශෲ");
    text = text.replace(/nDD/g, "බෲ");
    text = text.replace(/vDD/g, "ඩෲ");
    text = text.replace(/\*DD/g, "ෆෲ");
    text = text.replace(/\.DD/g, "ගෲ");
    text = text.replace(/lDD/g, "කෲ");
    text = text.replace(/mDD/g, "පෲ");
    text = text.replace(/iDD/g, "සෲ");
    text = text.replace(/gDD/g, "ටෲ");
    text = text.replace(/\;DD/g, "තෲ");
    text = text.replace(/NDD/g, "භෲ");
    text = text.replace(/ODD/g, "ධෲ");
    text = text.replace(/Ï/g, "ඐ");
    text = text.replace(/rE/g, "රූ");
    text = text.replace(/W!/g, "ඌ");
    text = text.replace(/T!/g, "ඖ");
    text = text.replace(/Ï/g, "ඐ");
    text = text.replace(/Æ/g, "ලූ");
    text = text.replace(/re/g, "රු");
    text = text.replace(/\//g, "රැ=");
    text = text.replace(/ƒ/g, "ඳැ=");
    text = text.replace(/\//g, "රැ");
    text = text.replace(/R/g, "ඍ");
    text = text.replace(/¨/g, "ලූ");
    text = text.replace(/§/g, "දී");
    text = text.replace(/ø/g, "ද්‍ර");
    text = text.replace(/÷/g, "ඳු");
    text = text.replace(/ÿ/g, "දු");
    text = text.replace(/ü/g, "ඤූ=");
    text = text.replace(/û/g, "ඤු=");
    text = text.replace(/£/g, "ඳී");
    text = text.replace(/°/g, "ඣී");
    text = text.replace(/Á/g, "ඨී");
    text = text.replace(/Â/g, "ඡී");
    text = text.replace(/Ç/g, "ඛී");
    text = text.replace(/Í/g, "රී");
    text = text.replace(/Ð/g, "ඪී");
    text = text.replace(/Ò/g, "ථී");
    text = text.replace(/Ô/g, "ජී");
    text = text.replace(/Ö/g, "චී");
    text = text.replace(/Ú/g, "ඵී");
    text = text.replace(/Ý/g, "ඵී");
    text = text.replace(/à/g, "ටී");
    text = text.replace(/ã/g, "ඞී");
    text = text.replace(/é/g, "ඬී");
    text = text.replace(/ë/g, "ධී");
    text = text.replace(/î/g, "බී");
    text = text.replace(/ó/g, "මී");
    text = text.replace(/ö/g, "ඹී");
    text = text.replace(/ù/g, "වී");
    text = text.replace(/Ú/g, "ඵී");
    text = text.replace(/Œ/g, "ණී");
    text = text.replace(/B/g, "ඊ");
    text = text.replace(/b/g, "ඉ");
    text = text.replace(/¢/g, "ඳි");
    text = text.replace(/È/g, "දි");
    text = text.replace(/¯/g, "ඣි");
    text = text.replace(/À/g, "ඨි");
    text = text.replace(/Å/g, "ඛි");
    text = text.replace(/È/g, "දි");
    text = text.replace(/ß/g, "රි");
    text = text.replace(/Î/g, "ඪි");
    text = text.replace(/Ñ/g, "චි");
    text = text.replace(/Ó/g, "ථි");
    text = text.replace(/á/g, "ටි");
    text = text.replace(/ä/g, "ඩි");
    text = text.replace(/ç/g, "ඬි");
    text = text.replace(/ê/g, "ධි");
    text = text.replace(/ì/g, "බි");
    text = text.replace(/ñ/g, "මි");
    text = text.replace(/ð/g, "ජි");
    text = text.replace(/ô/g, "ඹි");
    text = text.replace(/ú/g, "වි");
    text = text.replace(/Ý/g, "ඵි");
    text = text.replace(/ˉ/g, "ඣි");
    text = text.replace(/‚/g, "ණි");
    text = text.replace(/þ/g, "ඡ්");
    text = text.replace(/Ü/g, "ට්");
    text = text.replace(/Ù/g, "ඕ");
    text = text.replace(/õ/g, "ව්");
    text = text.replace(/ò/g, "ඹ්");
    text = text.replace(/ï/g, "ම්");
    text = text.replace(/í/g, "බ්");
    text = text.replace(/è/g, "ධ්");
    text = text.replace(/å/g, "ඬ්");
    text = text.replace(/â/g, "ඞ්");
    text = text.replace(/Ü/g, "ට්");
    text = text.replace(/Ù/g, "ඩ්");
    text = text.replace(/´/g, " ඕ");
    text = text.replace(/¾/g, "ර්");
    text = text.replace(/Ä/g, "ඛ්");
    text = text.replace(/É/g, "ච්");
    text = text.replace(/Ê/g, "ජ්");
    text = text.replace(/wd/g, "ආ");
    text = text.replace(/we/g, "ඇ");
    text = text.replace(/P/g, "ඡ=");
    text = text.replace(/X/g, "ඞ");
    text = text.replace(/r/g, "ර");
    text = text.replace(/Ì/g, "ඏ");
    text = text.replace(/“/g, " ර්‍ණ");
    text = text.replace(/I/g, "ෂ");
    text = text.replace(/U/g, "ඹ");
    text = text.replace(/c/g, "ජ");
    text = text.replace(/V/g, "ඪ");
    text = text.replace(/>/g, "ඝ");
    text = text.replace(/CO/g, "ඣ");
    text = text.replace(/L/g, "ඛ");
    text = text.replace(/</g, "ළ");
    text = text.replace(/\`y/g, "ඟ");
    text = text.replace(/K/g, "ණ");
    text = text.replace(/M/g, "ඵ");
    text = text.replace(/G/g, "ඨ");
    text = text.replace(/¿/g, "ළු");
    text = text.replace(/~/g, "ඬ");
    text = text.replace(/Y/g, "ශ");
    text = text.replace(/{/g, "ඥ");
    text = text.replace(/\|/g, "ඳ");
    text = text.replace(/Ë/g, "ක්‍ෂ");
    text = text.replace(/CI/g, "ක්‍ෂ");
    text = text.replace(/®/g, "ඣ");
    text = text.replace(/Õ/g, "ඟ");
    text = text.replace(/×/g, "ඥ");
    text = text.replace(/Ø/g, "ඤ");
    text = text.replace(/Ì/g, "ඏ");
    text = text.replace(/t/g, "එ");
    text = text.replace(/w/g, "අ");
    text = text.replace(/n/g, "බ");
    text = text.replace(/p/g, "ච");
    text = text.replace(/v/g, "ඩ");
    text = text.replace(/M/g, "ඵ");
    text = text.replace(/\*/g, "ෆ");
    text = text.replace(/\./g, "ග");
    text = text.replace(/y/g, "හ");
    text = text.replace(/c/g, "ජ");
    text = text.replace(/l/g, "ක");
    text = text.replace(/,/g, "ල");
    text = text.replace(/u/g, "ම");
    text = text.replace(/k/g, "න");
    text = text.replace(/T/g, "ඔ");
    text = text.replace(/m/g, "ප");
    text = text.replace(/o/g, "ද");
    text = text.replace(/r/g, "ර");
    text = text.replace(/i/g, "ස");
    text = text.replace(/g/g, "ට");
    text = text.replace(/W/g, "උ");
    text = text.replace(/j/g, "ව");
    text = text.replace(/;/g, "ත");
    text = text.replace(/N/g, "භ");
    text = text.replace(/h/g, "ය");
    text = text.replace(/\[/g, "ඤ");
    text = text.replace(/\{/g, "ඥ");
    text = text.replace(/\|/g, "ඳ");
    text = text.replace(/~/g, "ඬ");
    text = text.replace(/O/g, "ධ");
    text = text.replace(/\:/g, "ථ");
    text = text.replace(/\…/g, "ත්‍ව");
    text = text.replace(/‡/g, "න්‍ද");
    text = text.replace(/†/g, "ත්‍ථ");
    text = text.replace(/F/g, "ත්‍");
    text = text.replace(/J/g, "න්‍");
    text = text.replace(/C/g, "ක්‍");
    text = text.replace(/Þ/g, "දා");
    text = text.replace(/±/g, "දැ");
    text = text.replace(/ˆ/g, "න්‍දා");
    text = text.replace(/H/g, "්‍ය");
    text = text.replace(/%/g, "‍්‍ර");
    text = text.replace(/f/g, "ෙ");
    text = text.replace(/e/g, "ැ");
    text = text.replace(/E/g, "ෑ");
    text = text.replace(/q/g, "ු");
    text = text.replace(/s/g, "ි");
    text = text.replace(/Q/g, "ූ");
    text = text.replace(/=/g, "ු");
    text = text.replace(/\+/g, "ූ");
    text = text.replace(/S/g, "ී");
    text = text.replace(/D/g, "ෘ");
    text = text.replace(/!/g, "ෟ");
    text = text.replace(/d/g, "ා");
    text = text.replace(/A/g, "්");
    text = text.replace(/a/g, "්");
    text = text.replace(/x/g, "ං");
    text = text.replace(/½/g, "ඃ");
    text = text.replace(/ ’/g, "ී");
    text = text.replace(/ ‘/g, "ි");
    text = text.replace(/#/g, "ඃ");
    text = text.replace(/œ/g, " ර්‍්‍ය");
    text = text.replace(/˜/g, "”");
    text = text.replace(/—/g, "”");
    text = text.replace(/™/g, "{");
    text = text.replace(/∙/g, "×");
    text = text.replace(/š/g, "}");
    text = text.replace(/•/g, "x");
    text = text.replace(/²/g, "•");
    text = text.replace(/­/g, "÷");
    text = text.replace(/¬/g, "+");
    text = text.replace(/«/g, "×");
    text = text.replace(/}/g, "=");
    text = text.replace(/−/g, "÷");
    text = text.replace(/"/g, "□");
    text = text.replace(/æ/g, "!");
    text = text.replace(/\$/g, "/");
    text = text.replace(/\&/g, ")");
    text = text.replace(/\(/g, ":");
    text = text.replace(/\)/g, "*");
    text = text.replace(/-/g, "-");
    text = text.replace(/@/g, "?");
    text = text.replace(/Z/g, "’");
    text = text.replace(/z/g, "‘");
    text = text.replace(/\]/g, "%");
    text = text.replace(/\^/g, "(");
    text = text.replace(/&/g, ")");
    text = text.replace(/Z/g, "’");
    text = text.replace(/¡/g, "•");
    text = text.replace(/¤/g, "–");
    text = text.replace(/\¦/g, ";");
    text = text.replace(/\³/g, "⋆");
    text = text.replace(/µ/g, "i");
    text = text.replace(/¶/g, "v");
    text = text.replace(/•/g, "x");
    text = text.replace(/¸/g, "I");
    text = text.replace(/¹/g, "V");
    text = text.replace(/º/g, "X");
    text = text.replace(/º/g, "X");
    text = text.replace(/¹/g, "V");
    text = text.replace(/Ã/g, ".");
    text = text.replace(/»/g, ",");

    return text;
}
