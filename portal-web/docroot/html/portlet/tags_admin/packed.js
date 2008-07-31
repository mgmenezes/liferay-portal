Liferay.Portlet.TagsAdmin=new Class({VOCABULARY_SELECTED:"tag",EXP_ENTRY_SCOPE:".ui-tags-vocabulary-entries",EXP_VOCABULARY_SCOPE:".ui-tags-vocabulary-list",EXP_ENTRY_LIST:".ui-tags-vocabulary-entries li",EXP_VOCABULARY_LIST:".ui-tags-vocabulary-list li",selectedVocabularyId:null,selectedVocabularyName:null,selectedEntryName:null,selectedEntryId:null,messageTimeout:null,initialize:function(G){var A=this;var B=jQuery(A.EXP_ENTRY_SCOPE);jQuery(".ui-tags-close").click(function(){A._unselectAllEntries();A._closeEditSection()});jQuery(".ui-tags-save-properties").click(function(){A._saveProperties()});var E=jQuery(".ui-tags-buttons"),D=jQuery(".ui-tags-toolbar");var F=function(I){D.find(".ui-tags-label").html(I)},H=function(I){E.find(".button").removeClass("selected");jQuery(I).addClass("selected")};E.find(".tags-sets").click(function(){A.VOCABULARY_SELECTED="tag";F("Add tag");H(this);A._loadData()});E.find(".categories").click(function(){A.VOCABULARY_SELECTED="category";F("Add category");H(this);A._loadData()});jQuery("select.ui-tags-select-list").change(function(){var L=jQuery(".ui-tags-actions"),I=jQuery(this).val(),K=jQuery(this).find("option:selected").text(),J=L.find(".ui-tags-vocabulary-name");if(K=="(new)"){J.show().focus()}else{A._resetActionValues()}});var C=function(){var N=jQuery(".ui-tags-actions"),M=N.find(".ui-tags-entry-name").val(),J=N.find(".ui-tags-select-list").val(),L=N.find(".ui-tags-select-list option:selected").text(),K=N.find(".ui-tags-vocabulary-name");A._hideAllMessages();var I=K.val();if(I){A._addVocabulary(I,function(){A._addEntry(M,I)});return }A._addEntry(M,L)};jQuery("input.ui-tags-save-entry").click(C);jQuery(".ui-tags-actions input").keyup(function(I){if(I.keyCode==13){C();I.preventDefault()}});jQuery("input.ui-tags-delete-entries-button").click(function(){var J=jQuery(A.EXP_ENTRY_SCOPE+" :checked"),I=confirm(Liferay.Language.get("are-you-sure-you-want-to-delete-this-list")),K=J.size();if(I){J.each(function(M){var P=jQuery(this).parents("li:first"),O=A._getEntryId(P),N=M+1==K,L=function(){A._sendMessage("success","your-request-processed-successfully");A.displayVocabularyEntries(A.selectedVocabularyName);A._closeEditSection()};A._deleteEntry(O,N?L:undefined)})}});this._loadData()},_loadData:function(){var A=this,B=A.VOCABULARY_SELECTED,C=/category/.test(B)?"displayCategoriesList":"displayFolksonomiesList";this._closeEditSection();this[C](function(){A.displayVocabularyEntries(A.selectedVocabularyName,function(){var D=A._getEntryId(A.EXP_ENTRY_LIST+":first")})})},getVocabularies:function(A,B){Liferay.Service.Tags.TagsVocabulary.getVocabularies({companyId:themeDisplay.getCompanyId(),folksonomy:A},B)},getFolksonomies:function(A){this.getVocabularies(true,A)},getCategories:function(A){this.getVocabularies(false,A)},displayFolksonomiesList:function(A){this.displayList(true,A)},displayCategoriesList:function(A){this.displayList(false,A)},displayList:function(C,D){var A=this,B=[],F=jQuery(A.EXP_VOCABULARY_SCOPE),E=C?"getFolksonomies":"getCategories";A._showLoading(".ui-tags-vocabulary-entries, .ui-tags-vocabulary-list");B.push("<ul>");this[E](function(J){jQuery.each(J,function(L){B.push("<li");B.push(" class='ui-tags-vocabulary ");if(L==0){B.push(" selected ")}B.push("' data-vocabulary='");B.push(this.name);B.push("' data-vocabularyId='");B.push(this.vocabularyId);B.push("'><a href='javascript:void(0);'>");B.push(this.name);B.push("</a></li>")});B.push("</ul>");F.html(B.join(""));var I=jQuery(A.EXP_VOCABULARY_LIST+":first"),H=A._getVocabularyName(I),G=A._getVocabularyId(I);A.selectedVocabularyName=H;A.selectedVocabularyId=G;A._feedVocabularySelect(J,G);var K=jQuery("li",F);K.mousedown(function(){var L=A._getVocabularyId(this);A._selectVocabulary(L)});K.droppable({accept:".ui-tags-item",tolerance:"pointer",hoverClass:"active-area",scroll:"auto",scope:"ui-tags-item-scope",cssNamespace:false,drop:function(L,M){M.droppable=jQuery(this);A._merge(L,M)}});if(D){D()}})},getVocabularyEntries:function(A,B){this._showLoading(this.EXP_ENTRY_SCOPE);Liferay.Service.Tags.TagsEntry.getVocabularyEntries({companyId:themeDisplay.getCompanyId(),name:A},B)},displayVocabularyEntries:function(B,C){var A=this;this.getVocabularyEntries(B,function(D){if(!A.VOCABULARY_SELECTED||A.VOCABULARY_SELECTED=="tag"){A.displayFolksonomiesVocabularyEntries(D,C)}if(A.VOCABULARY_SELECTED=="category"){A.displayCategoriesVocabularyEntries(D,C)}})},displayFolksonomiesVocabularyEntries:function(B,D){var A=this,C=[],F=jQuery(A.EXP_ENTRY_SCOPE);C.push("<ul>");jQuery.each(B,function(G){C.push("<li class='ui-tags-item' ");C.push("data-entry='");C.push(this.name);C.push("' data-entryId='");C.push(this.entryId);C.push("'><span><input type='checkbox'/> <a href='javascript:void(0);'>");C.push(this.name);C.push("</a></span>");C.push("</li>")});C.push("</ul>");if(B.length==0){C=[];A._sendMessage("error","no-entries-were-found","#ui-tags-entry-messages",true)}F.html(C.join(""));var E=jQuery(A.EXP_ENTRY_LIST);E.mousedown(function(){var G=A._getEntryId(this),H=jQuery(".ui-tags-vocabulary-edit");A._selectEntry(G);A._showSection(H)});E.draggable({appendTo:"body",helper:function(H,I){var G=jQuery(this);return G.clone().css({width:G.width()})},cursor:"move",scroll:"auto",distance:3,ghosting:false,opacity:0.7,zIndex:1000,scope:"ui-tags-item-scope",cssNamespace:false});E.droppable({accept:".ui-tags-item",tolerance:"pointer",hoverClass:"active-area",cssNamespace:false,scope:"ui-tags-item-scope",drop:function(G,H){H.droppable=jQuery(this);A._merge(G,H)}});A._paintOddLines();if(D){D()}},displayCategoriesVocabularyEntries:function(D,I){var J=this,C=[],H=jQuery(J.EXP_ENTRY_SCOPE);var F={sortOn:"li",dropOn:"span.folder",dropHoverClass:"hover-folder",drop:function(K,L){L.droppable=jQuery(jQuery(this).parent());J._merge(K,L);setTimeout(function(){jQuery("#ui-tags-treeview :not(span)").removeClass();jQuery("#ui-tags-treeview div").remove();jQuery("#ui-tags-treeview").removeData("toggler");jQuery("#ui-tags-treeview").treeview()},100)}};C.push("<div class=\"ui-tags-treeview-container\"><ul id=\"ui-tags-treeview\" class=\"filetree\">");J._buildCategoryTreeview(D,C,0);C.push("</ul></div>");H.html(C.join(""));var A=jQuery(J.EXP_ENTRY_LIST);A.click(function(L){var K=J._getEntryId(this),M=jQuery(".ui-tags-vocabulary-edit");J._selectEntry(K);J._showSection(M);L.stopPropagation()});jQuery("#ui-tags-treeview").treeview().tree(F);var G=jQuery(J.EXP_VOCABULARY_SCOPE),E=jQuery("li",G),B=jQuery("#ui-tags-treeview").data("tree").identifier;E.droppable({accept:".ui-tags-category-item",tolerance:"pointer",hoverClass:"active-area",scope:B,cssNamespace:false,drop:function(K,L){L.droppable=jQuery(this);J._merge(K,L)}});if(I){I()}},_buildCategoryTreeview:function(B,C,E){var A=this,D=A._filterCategory(B,E);jQuery.each(D,function(G){var H=this.entryId,F=this.name,I=A._filterCategory(B,H).length;C.push("<li");C.push(" class=\"ui-tags-category-item\"");C.push(" data-entry=\"");C.push(this.name);C.push("\" data-entryId=\"");C.push(this.entryId);C.push("\"><span class=\"folder\">");C.push(F);C.push("</span>");if(I){C.push("<ul>");A._buildCategoryTreeview(B,C,H);C.push("</ul>")}C.push("</li>")});return D.length},_filterCategory:function(A,C){var B=[];jQuery.each(A,function(D){if(this.parentEntryId==C){B.push(this)}});return B},getProperties:function(B,A){Liferay.Service.Tags.TagsProperty.getProperties({entryId:B},A)},displayProperties:function(B){var A=this;this.getProperties(B,function(D){if(!D.length){D=[{key:"",value:""}]}var E=D.length,C=jQuery("div.ui-tags-property-line").size();if(C>E){return }jQuery.each(D,function(){var F=jQuery("div.ui-tags-property-line:last");A._addProperty(F,this.key,this.value)})})},_addProperty:function(F,D,E){var B=this;var A=jQuery("div.ui-tags-property-line:last");var C=A.clone();C.find(".property-key").val(D);C.find(".property-value").val(E);C.insertAfter(F).css("display","block");C.find("input:first").focus();B._attachPropertyIconEvents(C)},_removeProperty:function(A){if(jQuery("div.ui-tags-property-line").length>2){A.remove()}},_attachPropertyIconEvents:function(B){var A=this;jQuery(B).find("img[title=Add]").click(function(){A._addProperty(B,"","")});jQuery(B).find("img[title=Delete]").click(function(){A._removeProperty(B)})},_showSection:function(B){var A=jQuery(B);if(!A.is(":visible")){A.fadeIn().find("input:first").focus()}},_hideSection:function(A){jQuery(A).hide()},_unselectAllEntries:function(){jQuery(this.EXP_ENTRY_LIST).removeClass("selected");jQuery("div.ui-tags-property-line:gt(0)").remove()},_unselectAllVocabularies:function(){jQuery(this.EXP_VOCABULARY_LIST).removeClass("selected")},_feedVocabularySelect:function(D,C){var A=jQuery("select.ui-tags-select-list"),B=["<option value=\"0\">(new)</option>"];jQuery.each(D,function(E){var F=this.vocabularyId==C;B.push("<option");B.push(F?" selected ":"");B.push(" value='");B.push(this.vocabularyId);B.push("'>");B.push(this.name);B.push("</option>")});A.html(B.join(""))},_selectCurrentVocabulary:function(B){var A=jQuery("select.ui-tags-select-list option[value=\""+B+"\"]");A.attr("selected","selected")},_updateEntry:function(F,D,C,E,B){var A=this;Liferay.Service.Tags.TagsEntry.updateEntry({entryId:F,parentEntryName:C,name:D,vocabularyName:B,properties:E},function(H){var G=H.exception;if(!G){A._closeEditSection()}else{if(/NoSuchVocabularyException/.test(G)){A._sendMessage("error","that-vocabulary-does-not-exist")}else{if(/NoSuchEntryException/.test(G)){A._sendMessage("error","that-parent-category-does-not-exist")}else{if(/Exception/.test(G)){A._sendMessage("error","one-of-your-fields-contain-invalid-characters")}}}}})},_addEntry:function(E,C,D){var A=this,B=["0:category:category"];Liferay.Service.Tags.TagsEntry.addEntry({name:E,vocabulary:C,properties:B},function(H){var G=H.exception;if(!G&&H.entryId){A._sendMessage("success","your-request-processed-successfully");A._selectVocabulary(H.vocabularyId);A.displayVocabularyEntries(A.selectedVocabularyName,function(){var I=A._selectEntry(H.entryId);if(I.length){jQuery(A.EXP_ENTRY_SCOPE).scrollTo(I)}A._showSection(".ui-tags-vocabulary-edit")});A._resetActionValues();if(D){D(E,C)}}else{var F;if(/DuplicateEntryException/.test(G)){F="that-tag-already-exists"}else{if(/EntryNameException/.test(G)){F="one-of-your-fields-contain-invalid-characters"}else{if(/NoSuchVocabularyException/.test(G)){F="that-vocabulary-does-not-exists"}}}if(F){A._sendMessage("error",F)}}})},_addVocabulary:function(C,D){var A=this,B=A.VOCABULARY_SELECTED=="tag";Liferay.Service.Tags.TagsVocabulary.addVocabulary({name:C,folksonomy:B},function(G){var F=G.exception;if(!G.exception){A._sendMessage("success","your-request-processed-successfully");var H=A.VOCABULARY_SELECTED,I=/category/.test(H)?"displayCategoriesList":"displayFolksonomiesList";A[I](function(){var J=A._selectVocabulary(G.vocabularyId);A.displayVocabularyEntries(A.selectedVocabularyName);if(J.length){jQuery(A.EXP_VOCABULARY_SCOPE).scrollTo(J)}});if(D){D(C)}}else{var E;if(/DuplicateVocabularyException/.test(F)){E="that-vocabulary-already-exists"}else{if(/VocabularyNameException/.test(F)){E="one-of-your-fields-contain-invalid-characters"}else{if(/NoSuchVocabularyException/.test(F)){E="that-parent-vocabulary-does-not-exist"}}}if(E){A._sendMessage("error",E)}}})},_deleteEntry:function(B,A){Liferay.Service.Tags.TagsEntry.deleteEntry({entryId:B},A)},_selectVocabulary:function(B){var A=this,D=A._getVocabulary(B),C=A._getVocabularyName(D),B=A._getVocabularyId(D);if(D.is(".selected")){return D}A._hideAllMessages();A.selectedVocabularyName=C;A.selectedVocabularyId=B;A._selectCurrentVocabulary(B);A._unselectAllVocabularies();A._closeEditSection();D.addClass("selected");A.displayVocabularyEntries(A.selectedVocabularyName);return D},_selectEntry:function(C){var B=this,E=B._getEntry(C),C=B._getEntryId(E),D=B._getEntryName(E);B.selectedEntryId=C;B.selectedEntryName=D;if(E.is(".selected")||!C){return E}B._unselectAllEntries();E.addClass("selected");var F=jQuery(".ui-tags-vocabulary-edit"),A=F.find("input.entry-name");A.val(D);B.displayProperties(C);return E},_mergeEntries:function(B,A,C){Liferay.Service.Tags.TagsEntry.mergeEntries({fromEntryId:B,toEntryId:A},C)},_merge:function(B,L){var O=this,P=L.draggable,H=L.droppable,G=O._getEntryId(P),J=O._getEntryName(P),D=O._getEntryId(H),C=O._getEntryName(H),E=O._getVocabularyId(H),F=O._getVocabularyName(H);var M=!!F,K=M?F:C;var A={SOURCE:O._getEntryName(P),DESTINATION:K},N=Liferay.Language.get("are-you-sure-you-want-to-merge-x-into-x",["{SOURCE}","{DESTINATION}"]).replace(/\{(SOURCE|DESTINATION)\}/gm,function(T,R,Q,S){return A[R]});if(confirm(N)){if(this.VOCABULARY_SELECTED=="tag"){if(M){var I=O._buildProperties();O._updateEntry(G,J,null,I,F);O.displayVocabularyEntries(O.selectedVocabularyName)}else{O._mergeEntries(G,D,function(){P.remove();O._selectEntry(D);O._paintOddLines()})}}else{if(this.VOCABULARY_SELECTED=="category"){var I=O._buildProperties();F=F||O.selectedVocabularyName;parentEntryName=M?null:C;O._updateEntry(G,J,parentEntryName,I,F)}}}},_resetActionValues:function(){jQuery(".ui-tags-actions input:text").val("");jQuery(".ui-tags-actions .ui-tags-vocabulary-name").hide()},_getVocabularyId:function(A){return jQuery(A).attr("data-vocabularyId")},_getVocabularyName:function(A){return jQuery(A).attr("data-vocabulary")},_getEntry:function(A){return jQuery("li[data-entryId="+A+"]")},_getVocabulary:function(A){return jQuery("li[data-vocabularyId="+A+"]")},_getEntryId:function(A){return jQuery(A).attr("data-entryId")},_getEntryName:function(A){return jQuery(A).attr("data-entry")},_saveProperties:function(){var A=this,E=A.selectedEntryId,F=A.selectedEntryName,B=null,D=A._buildProperties(),C=A.selectedVocabularyName;A._updateEntry(E,F,B,D,C)},_buildProperties:function(){var A=[];jQuery(".ui-tags-property-line:visible").each(function(D,F){var B=jQuery(this),C=B.find("input.property-key").val(),E=B.find("input.property-value").val();A.push(["0",":",C,":",E,","].join(""))});return A.join("")},_closeEditSection:function(){this._hideSection(".ui-tags-vocabulary-edit")},_hideAllMessages:function(){jQuery("#ui-tags-entry-messages, #ui-tags-messages").hide()},_showLoading:function(A){jQuery(A).html("<div class='loading-animation'></div>")},_hideLoading:function(A){jQuery("div.loading-animation").remove()},_sendMessage:function(E,C,B,D){var A=this,B=jQuery(B||"#ui-tags-messages"),F=Liferay.Language.get(C),G="portlet-msg-"+(E||"error");clearTimeout(A.messageTimeout);B.removeClass("portlet-msg-error portlet-msg-success");B.addClass(G).html(F).fadeIn("fast");if(!D){A.messageTimeout=setTimeout(function(){B.fadeOut("slow")},7000)}},_paintOddLines:function(){var A=jQuery(this.EXP_ENTRY_SCOPE);jQuery("li",A).removeClass("odd");jQuery("li:odd",A).addClass("odd")}})