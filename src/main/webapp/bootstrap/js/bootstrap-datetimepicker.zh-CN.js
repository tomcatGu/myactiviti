




<!DOCTYPE html>
<html class="   ">
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# object: http://ogp.me/ns/object# article: http://ogp.me/ns/article# profile: http://ogp.me/ns/profile#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    
    <title>bootstrap-datetimepicker/src/js/locales/bootstrap-datetimepicker.zh-CN.js at master · tarruda/bootstrap-datetimepicker</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub" />
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png" />
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png" />
    <meta property="fb:app_id" content="1401488693436528"/>

      <meta content="@github" name="twitter:site" /><meta content="summary" name="twitter:card" /><meta content="tarruda/bootstrap-datetimepicker" name="twitter:title" /><meta content="bootstrap-datetimepicker - Date/time picker widget based on twitter bootstrap" name="twitter:description" /><meta content="https://avatars0.githubusercontent.com/u/842846?s=400" name="twitter:image:src" />
<meta content="GitHub" property="og:site_name" /><meta content="object" property="og:type" /><meta content="https://avatars0.githubusercontent.com/u/842846?s=400" property="og:image" /><meta content="tarruda/bootstrap-datetimepicker" property="og:title" /><meta content="https://github.com/tarruda/bootstrap-datetimepicker" property="og:url" /><meta content="bootstrap-datetimepicker - Date/time picker widget based on twitter bootstrap" property="og:description" />

    <link rel="assets" href="https://github.global.ssl.fastly.net/">
    <link rel="conduit-xhr" href="https://ghconduit.com:25035/">
    <link rel="xhr-socket" href="/_sockets" />

    <meta name="msapplication-TileImage" content="/windows-tile.png" />
    <meta name="msapplication-TileColor" content="#ffffff" />
    <meta name="selected-link" value="repo_source" data-pjax-transient />
    <meta content="collector.githubapp.com" name="octolytics-host" /><meta content="collector-cdn.github.com" name="octolytics-script-host" /><meta content="github" name="octolytics-app-id" /><meta content="7A6024D1:4611:1A176C:5370CD62" name="octolytics-dimension-request_id" /><meta content="6365837" name="octolytics-actor-id" /><meta content="tomcatGu" name="octolytics-actor-login" /><meta content="ac89e07589439f28387ef37eecbc0ea802b67774a724cee60da80d03ffe63764" name="octolytics-actor-hash" />
    

    
    
    <link rel="icon" type="image/x-icon" href="https://github.global.ssl.fastly.net/favicon.ico" />

    <meta content="authenticity_token" name="csrf-param" />
<meta content="0f4T+/SOqBiXGLmFLTxVeAshgwQUfSqigvMidvNA7PoijJenwMsDsCwljKBLXBYcMfYvhvWgY+cmuOdq0H5Pyw==" name="csrf-token" />

    <link href="https://github.global.ssl.fastly.net/assets/github-58e181c5cf58206dac2f13d435da7a71ca165593.css" media="all" rel="stylesheet" type="text/css" />
    <link href="https://github.global.ssl.fastly.net/assets/github2-1a3c410b868af7031a33d9c381adc57fbdd76b68.css" media="all" rel="stylesheet" type="text/css" />
    


    <meta http-equiv="x-pjax-version" content="8d3812e005f9ff2b254914e5f873c6f0">

      
  <meta name="description" content="bootstrap-datetimepicker - Date/time picker widget based on twitter bootstrap" />

  <meta content="842846" name="octolytics-dimension-user_id" /><meta content="tarruda" name="octolytics-dimension-user_login" /><meta content="7193007" name="octolytics-dimension-repository_id" /><meta content="tarruda/bootstrap-datetimepicker" name="octolytics-dimension-repository_nwo" /><meta content="true" name="octolytics-dimension-repository_public" /><meta content="false" name="octolytics-dimension-repository_is_fork" /><meta content="7193007" name="octolytics-dimension-repository_network_root_id" /><meta content="tarruda/bootstrap-datetimepicker" name="octolytics-dimension-repository_network_root_nwo" />
  <link href="https://github.com/tarruda/bootstrap-datetimepicker/commits/master.atom" rel="alternate" title="Recent Commits to bootstrap-datetimepicker:master" type="application/atom+xml" />

  </head>


  <body class="logged_in  env-production windows vis-public page-blob">
    <a href="#start-of-content" tabindex="1" class="accessibility-aid js-skip-to-content">Skip to content</a>
    <div class="wrapper">
      
      
      
      


      <div class="header header-logged-in true">
  <div class="container clearfix">

    <a class="header-logo-invertocat" href="https://github.com/">
  <span class="mega-octicon octicon-mark-github"></span>
</a>

    
    <a href="/notifications" aria-label="You have no unread notifications" class="notification-indicator tooltipped tooltipped-s" data-hotkey="g n">
        <span class="mail-status all-read"></span>
</a>

      <div class="command-bar js-command-bar  in-repository">
          <form accept-charset="UTF-8" action="/search" class="command-bar-form" id="top_search_form" method="get">

<div class="commandbar">
  <span class="message"></span>
  <input type="text" data-hotkey="s, /" name="q" id="js-command-bar-field" placeholder="Search or type a command" tabindex="1" autocapitalize="off"
    
    data-username="tomcatGu"
      data-repo="tarruda/bootstrap-datetimepicker"
      data-branch="master"
      data-sha="95c0c23d435c1a1d379cf6a2f69247da05db18b0"
  >
  <div class="display hidden"></div>
</div>

    <input type="hidden" name="nwo" value="tarruda/bootstrap-datetimepicker" />

    <div class="select-menu js-menu-container js-select-menu search-context-select-menu">
      <span class="minibutton select-menu-button js-menu-target" role="button" aria-haspopup="true">
        <span class="js-select-button">This repository</span>
      </span>

      <div class="select-menu-modal-holder js-menu-content js-navigation-container" aria-hidden="true">
        <div class="select-menu-modal">

          <div class="select-menu-item js-navigation-item js-this-repository-navigation-item selected">
            <span class="select-menu-item-icon octicon octicon-check"></span>
            <input type="radio" class="js-search-this-repository" name="search_target" value="repository" checked="checked" />
            <div class="select-menu-item-text js-select-button-text">This repository</div>
          </div> <!-- /.select-menu-item -->

          <div class="select-menu-item js-navigation-item js-all-repositories-navigation-item">
            <span class="select-menu-item-icon octicon octicon-check"></span>
            <input type="radio" name="search_target" value="global" />
            <div class="select-menu-item-text js-select-button-text">All repositories</div>
          </div> <!-- /.select-menu-item -->

        </div>
      </div>
    </div>

  <span class="help tooltipped tooltipped-s" aria-label="Show command bar help">
    <span class="octicon octicon-question"></span>
  </span>


  <input type="hidden" name="ref" value="cmdform">

</form>
        <ul class="top-nav">
          <li class="explore"><a href="/explore">Explore</a></li>
            <li><a href="https://gist.github.com">Gist</a></li>
            <li><a href="/blog">Blog</a></li>
          <li><a href="https://help.github.com">Help</a></li>
        </ul>
      </div>

    


  <ul id="user-links">
    <li>
      <a href="/tomcatGu" class="name">
        <img alt="tomcatGu" class=" js-avatar" data-user="6365837" height="20" src="https://avatars1.githubusercontent.com/u/6365837?s=140" width="20" /> tomcatGu
      </a>
    </li>

    <li class="new-menu dropdown-toggle js-menu-container">
      <a href="#" class="js-menu-target tooltipped tooltipped-s" aria-label="Create new...">
        <span class="octicon octicon-plus"></span>
        <span class="dropdown-arrow"></span>
      </a>

      <div class="new-menu-content js-menu-content">
      </div>
    </li>

    <li>
      <a href="/settings/profile" id="account_settings"
        class="tooltipped tooltipped-s"
        aria-label="Account settings (You have no verified emails)">
        <span class="octicon octicon-tools"></span>
      </a>
        <span class="octicon octicon-alert settings-warning"></span>
    </li>
    <li>
      <form class="logout-form" action="/logout" method="post">
        <button class="sign-out-button tooltipped tooltipped-s" aria-label="Sign out">
          <span class="octicon octicon-log-out"></span>
        </button>
      </form>
    </li>

  </ul>

<div class="js-new-dropdown-contents hidden">
  

<ul class="dropdown-menu">
  <li>
    <a href="/new"><span class="octicon octicon-repo-create"></span> New repository</a>
  </li>
  <li>
    <a href="/organizations/new"><span class="octicon octicon-organization"></span> New organization</a>
  </li>


    <li class="section-title">
      <span title="tarruda/bootstrap-datetimepicker">This repository</span>
    </li>
      <li>
        <a href="/tarruda/bootstrap-datetimepicker/issues/new"><span class="octicon octicon-issue-opened"></span> New issue</a>
      </li>
</ul>

</div>


    
  </div>
</div>

      

        

<div class="flash-global js-notice flash-warn">
<div class="container">
    <h2>
      You don't have any verified emails.  We recommend <a href="https://github.com/settings/emails">verifying</a> at least one email.
    </h2>
    <p>
      Email verification helps our support team help you in case you have any email issues or lose your password.
    </p>


















</div>
</div>


      <div id="start-of-content" class="accessibility-aid"></div>
          <div class="site" itemscope itemtype="http://schema.org/WebPage">
    <div id="js-flash-container">
      
    </div>
    <div class="pagehead repohead instapaper_ignore readability-menu">
      <div class="container">
        

<ul class="pagehead-actions">

    <li class="subscription">
      <form accept-charset="UTF-8" action="/notifications/subscribe" class="js-social-container" data-autosubmit="true" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="TGlx2uzqQT1BcEpqaKmFTwtgN+ziddZwF4j4VRdtAR2/BxwEEOOI1QDk+/z+2QTToMrZMGwpbIcv+st/Ma523g==" /></div>  <input id="repository_id" name="repository_id" type="hidden" value="7193007" />

    <div class="select-menu js-menu-container js-select-menu">
      <a class="social-count js-social-count" href="/tarruda/bootstrap-datetimepicker/watchers">
        96
      </a>
      <span class="minibutton select-menu-button with-count js-menu-target" role="button" tabindex="0" aria-haspopup="true">
        <span class="js-select-button">
          <span class="octicon octicon-eye-watch"></span>
          Watch
        </span>
      </span>

      <div class="select-menu-modal-holder">
        <div class="select-menu-modal subscription-menu-modal js-menu-content" aria-hidden="true">
          <div class="select-menu-header">
            <span class="select-menu-title">Notification status</span>
            <span class="octicon octicon-remove-close js-menu-close"></span>
          </div> <!-- /.select-menu-header -->

          <div class="select-menu-list js-navigation-container" role="menu">

            <div class="select-menu-item js-navigation-item selected" role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input checked="checked" id="do_included" name="do" type="radio" value="included" />
                <h4>Not watching</h4>
                <span class="description">You only receive notifications for conversations in which you participate or are @mentioned.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-eye-watch"></span>
                  Watch
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

            <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input id="do_subscribed" name="do" type="radio" value="subscribed" />
                <h4>Watching</h4>
                <span class="description">You receive notifications for all conversations in this repository.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-eye-unwatch"></span>
                  Unwatch
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

            <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input id="do_ignore" name="do" type="radio" value="ignore" />
                <h4>Ignoring</h4>
                <span class="description">You do not receive any notifications for conversations in this repository.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-mute"></span>
                  Stop ignoring
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

          </div> <!-- /.select-menu-list -->

        </div> <!-- /.select-menu-modal -->
      </div> <!-- /.select-menu-modal-holder -->
    </div> <!-- /.select-menu -->

</form>
    </li>

  <li>
  

  <div class="js-toggler-container js-social-container starring-container ">

    <form accept-charset="UTF-8" action="/tarruda/bootstrap-datetimepicker/unstar" class="js-toggler-form starred" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="UolegI9E4tYD/ZIg4P+O6R3rOIRyfdfcY9qko7PL7OArh83U9TNlRv/sxPXIEgjFV0XeIJ6947WsZ3cl6zunPQ==" /></div>
      <button
        class="minibutton with-count js-toggler-target star-button"
        aria-label="Unstar this repository" title="Unstar tarruda/bootstrap-datetimepicker">
        <span class="octicon octicon-star-delete"></span><span class="text">Unstar</span>
      </button>
        <a class="social-count js-social-count" href="/tarruda/bootstrap-datetimepicker/stargazers">
          1,237
        </a>
</form>
    <form accept-charset="UTF-8" action="/tarruda/bootstrap-datetimepicker/star" class="js-toggler-form unstarred" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="XVPtpOcQ0l8MsQPsvx4Vie9zGaMc+vDc2eNVlIsgFnzwA04fiBWBYHxuV5xaiBLibRxqCiV5/TC2dKf0FUKINQ==" /></div>
      <button
        class="minibutton with-count js-toggler-target star-button"
        aria-label="Star this repository" title="Star tarruda/bootstrap-datetimepicker">
        <span class="octicon octicon-star"></span><span class="text">Star</span>
      </button>
        <a class="social-count js-social-count" href="/tarruda/bootstrap-datetimepicker/stargazers">
          1,237
        </a>
</form>  </div>

  </li>


        <li>
          <a href="/tarruda/bootstrap-datetimepicker/fork" class="minibutton with-count js-toggler-target fork-button lighter tooltipped-n" title="Fork your own copy of tarruda/bootstrap-datetimepicker to your account" aria-label="Fork your own copy of tarruda/bootstrap-datetimepicker to your account" rel="nofollow" data-method="post">
            <span class="octicon octicon-git-branch-create"></span><span class="text">Fork</span>
          </a>
          <a href="/tarruda/bootstrap-datetimepicker/network" class="social-count">902</a>
        </li>


</ul>

        <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
          <span class="repo-label"><span>public</span></span>
          <span class="mega-octicon octicon-repo"></span>
          <span class="author"><a href="/tarruda" class="url fn" itemprop="url" rel="author"><span itemprop="title">tarruda</span></a></span><!--
       --><span class="path-divider">/</span><!--
       --><strong><a href="/tarruda/bootstrap-datetimepicker" class="js-current-repository js-repo-home-link">bootstrap-datetimepicker</a></strong>

          <span class="page-context-loader">
            <img alt="Octocat-spinner-32" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
          </span>

        </h1>
      </div><!-- /.container -->
    </div><!-- /.repohead -->

    <div class="container">
      <div class="repository-with-sidebar repo-container new-discussion-timeline js-new-discussion-timeline  ">
        <div class="repository-sidebar clearfix">
            

<div class="sunken-menu vertical-right repo-nav js-repo-nav js-repository-container-pjax js-octicon-loaders">
  <div class="sunken-menu-contents">
    <ul class="sunken-menu-group">
      <li class="tooltipped tooltipped-w" aria-label="Code">
        <a href="/tarruda/bootstrap-datetimepicker" aria-label="Code" class="selected js-selected-navigation-item sunken-menu-item" data-hotkey="g c" data-pjax="true" data-selected-links="repo_source repo_downloads repo_commits repo_releases repo_tags repo_branches /tarruda/bootstrap-datetimepicker">
          <span class="octicon octicon-code"></span> <span class="full-word">Code</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

        <li class="tooltipped tooltipped-w" aria-label="Issues">
          <a href="/tarruda/bootstrap-datetimepicker/issues" aria-label="Issues" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-hotkey="g i" data-selected-links="repo_issues /tarruda/bootstrap-datetimepicker/issues">
            <span class="octicon octicon-issue-opened"></span> <span class="full-word">Issues</span>
            <span class='counter'>172</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>        </li>

      <li class="tooltipped tooltipped-w" aria-label="Pull Requests">
        <a href="/tarruda/bootstrap-datetimepicker/pulls" aria-label="Pull Requests" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-hotkey="g p" data-selected-links="repo_pulls /tarruda/bootstrap-datetimepicker/pulls">
            <span class="octicon octicon-git-pull-request"></span> <span class="full-word">Pull Requests</span>
            <span class='counter'>27</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>


        <li class="tooltipped tooltipped-w" aria-label="Wiki">
          <a href="/tarruda/bootstrap-datetimepicker/wiki" aria-label="Wiki" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-hotkey="g w" data-selected-links="repo_wiki /tarruda/bootstrap-datetimepicker/wiki">
            <span class="octicon octicon-book"></span> <span class="full-word">Wiki</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>        </li>
    </ul>
    <div class="sunken-menu-separator"></div>
    <ul class="sunken-menu-group">

      <li class="tooltipped tooltipped-w" aria-label="Pulse">
        <a href="/tarruda/bootstrap-datetimepicker/pulse" aria-label="Pulse" class="js-selected-navigation-item sunken-menu-item" data-pjax="true" data-selected-links="pulse /tarruda/bootstrap-datetimepicker/pulse">
          <span class="octicon octicon-pulse"></span> <span class="full-word">Pulse</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

      <li class="tooltipped tooltipped-w" aria-label="Graphs">
        <a href="/tarruda/bootstrap-datetimepicker/graphs" aria-label="Graphs" class="js-selected-navigation-item sunken-menu-item" data-pjax="true" data-selected-links="repo_graphs repo_contributors /tarruda/bootstrap-datetimepicker/graphs">
          <span class="octicon octicon-graph"></span> <span class="full-word">Graphs</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

      <li class="tooltipped tooltipped-w" aria-label="Network">
        <a href="/tarruda/bootstrap-datetimepicker/network" aria-label="Network" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-selected-links="repo_network /tarruda/bootstrap-datetimepicker/network">
          <span class="octicon octicon-git-branch"></span> <span class="full-word">Network</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>
    </ul>


  </div>
</div>

              <div class="only-with-full-nav">
                

  

<div class="clone-url open"
  data-protocol-type="http"
  data-url="/users/set_protocol?protocol_selector=http&amp;protocol_type=clone">
  <h3><strong>HTTPS</strong> clone URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="https://github.com/tarruda/bootstrap-datetimepicker.git" readonly="readonly">
    <span class="url-box-clippy">
    <button aria-label="copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-clipboard-text="https://github.com/tarruda/bootstrap-datetimepicker.git" data-copied-hint="copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  

<div class="clone-url "
  data-protocol-type="ssh"
  data-url="/users/set_protocol?protocol_selector=ssh&amp;protocol_type=clone">
  <h3><strong>SSH</strong> clone URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="git@github.com:tarruda/bootstrap-datetimepicker.git" readonly="readonly">
    <span class="url-box-clippy">
    <button aria-label="copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-clipboard-text="git@github.com:tarruda/bootstrap-datetimepicker.git" data-copied-hint="copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  

<div class="clone-url "
  data-protocol-type="subversion"
  data-url="/users/set_protocol?protocol_selector=subversion&amp;protocol_type=clone">
  <h3><strong>Subversion</strong> checkout URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="https://github.com/tarruda/bootstrap-datetimepicker" readonly="readonly">
    <span class="url-box-clippy">
    <button aria-label="copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-clipboard-text="https://github.com/tarruda/bootstrap-datetimepicker" data-copied-hint="copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>


<p class="clone-options">You can clone with
      <a href="#" class="js-clone-selector" data-protocol="http">HTTPS</a>,
      <a href="#" class="js-clone-selector" data-protocol="ssh">SSH</a>,
      or <a href="#" class="js-clone-selector" data-protocol="subversion">Subversion</a>.
  <span class="help tooltipped tooltipped-n" aria-label="Get help on which URL is right for you.">
    <a href="https://help.github.com/articles/which-remote-url-should-i-use">
    <span class="octicon octicon-question"></span>
    </a>
  </span>
</p>


  <a href="http://windows.github.com" class="minibutton sidebar-button" title="Save tarruda/bootstrap-datetimepicker to your computer and use it in GitHub Desktop." aria-label="Save tarruda/bootstrap-datetimepicker to your computer and use it in GitHub Desktop.">
    <span class="octicon octicon-device-desktop"></span>
    Clone in Desktop
  </a>

                <a href="/tarruda/bootstrap-datetimepicker/archive/master.zip"
                   class="minibutton sidebar-button"
                   aria-label="Download tarruda/bootstrap-datetimepicker as a zip file"
                   title="Download tarruda/bootstrap-datetimepicker as a zip file"
                   rel="nofollow">
                  <span class="octicon octicon-cloud-download"></span>
                  Download ZIP
                </a>
              </div>
        </div><!-- /.repository-sidebar -->

        <div id="js-repo-pjax-container" class="repository-content context-loader-container" data-pjax-container>
          

<a href="/tarruda/bootstrap-datetimepicker/blob/098742b313fc56b88e03bd11ff61539024d4c997/src/js/locales/bootstrap-datetimepicker.zh-CN.js" class="hidden js-permalink-shortcut" data-hotkey="y">Permalink</a>

<!-- blob contrib key: blob_contributors:v21:57871d82cb1a232c76d1701ef705e0bc -->

<p title="This is a placeholder element" class="js-history-link-replace hidden"></p>

<a href="/tarruda/bootstrap-datetimepicker/find/master" data-pjax data-hotkey="t" class="js-show-file-finder" style="display:none">Show File Finder</a>

<div class="file-navigation">
  

<div class="select-menu js-menu-container js-select-menu" >
  <span class="minibutton select-menu-button js-menu-target" data-hotkey="w"
    data-master-branch="master"
    data-ref="master"
    role="button" aria-label="Switch branches or tags" tabindex="0" aria-haspopup="true">
    <span class="octicon octicon-git-branch"></span>
    <i>branch:</i>
    <span class="js-select-button">master</span>
  </span>

  <div class="select-menu-modal-holder js-menu-content js-navigation-container" data-pjax aria-hidden="true">

    <div class="select-menu-modal">
      <div class="select-menu-header">
        <span class="select-menu-title">Switch branches/tags</span>
        <span class="octicon octicon-remove-close js-menu-close"></span>
      </div> <!-- /.select-menu-header -->

      <div class="select-menu-filters">
        <div class="select-menu-text-filter">
          <input type="text" aria-label="Filter branches/tags" id="context-commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Filter branches/tags">
        </div>
        <div class="select-menu-tabs">
          <ul>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="branches" class="js-select-menu-tab">Branches</a>
            </li>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="tags" class="js-select-menu-tab">Tags</a>
            </li>
          </ul>
        </div><!-- /.select-menu-tabs -->
      </div><!-- /.select-menu-filters -->

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="branches">

        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/blob/gh-pages/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="gh-pages"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="gh-pages">gh-pages</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item selected">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/blob/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="master"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="master">master</a>
            </div> <!-- /.select-menu-item -->
        </div>

          <div class="select-menu-no-results">Nothing to show</div>
      </div> <!-- /.select-menu-list -->

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="tags">
        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.11/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.11"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.11">v0.0.11</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.10/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.10"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.10">v0.0.10</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.9/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.9"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.9">v0.0.9</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.8/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.8"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.8">v0.0.8</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.7/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.7"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.7">v0.0.7</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.6/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.6"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.6">v0.0.6</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.5/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.5"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.5">v0.0.5</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.4/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.4"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.4">v0.0.4</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.3/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.3"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.3">v0.0.3</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.2/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.2"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.2">v0.0.2</a>
            </div> <!-- /.select-menu-item -->
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/tarruda/bootstrap-datetimepicker/tree/v0.0.1/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                 data-name="v0.0.1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="v0.0.1">v0.0.1</a>
            </div> <!-- /.select-menu-item -->
        </div>

        <div class="select-menu-no-results">Nothing to show</div>
      </div> <!-- /.select-menu-list -->

    </div> <!-- /.select-menu-modal -->
  </div> <!-- /.select-menu-modal-holder -->
</div> <!-- /.select-menu -->

  <div class="breadcrumb">
    <span class='repo-root js-repo-root'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/tarruda/bootstrap-datetimepicker" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">bootstrap-datetimepicker</span></a></span></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/tarruda/bootstrap-datetimepicker/tree/master/src" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">src</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/tarruda/bootstrap-datetimepicker/tree/master/src/js" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">js</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/tarruda/bootstrap-datetimepicker/tree/master/src/js/locales" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">locales</span></a></span><span class="separator"> / </span><strong class="final-path">bootstrap-datetimepicker.zh-CN.js</strong> <button aria-label="copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-clipboard-text="src/js/locales/bootstrap-datetimepicker.zh-CN.js" data-copied-hint="copied!" type="button"><span class="octicon octicon-clippy"></span></button>
  </div>
</div>


  <div class="commit file-history-tease">
    <img alt="Thiago de Arruda" class="main-avatar js-avatar" data-user="842846" height="24" src="https://avatars3.githubusercontent.com/u/842846?s=140" width="24" />
    <span class="author"><a href="/tarruda" rel="author">tarruda</a></span>
    <local-time datetime="2012-12-16T15:20:55-03:00" from="now" title-format="%Y-%m-%d %H:%M:%S %z" title="2012-12-16 15:20:55 -0300">December 16, 2012</local-time>
    <div class="commit-title">
        <a href="/tarruda/bootstrap-datetimepicker/commit/f64b84e02b8a74d122b1cfc2de56d317e8337c7d" class="message" data-pjax="true" title="Renamed files to match new package name">Renamed files to match new package name</a>
    </div>

    <div class="participation">
      <p class="quickstat"><a href="#blob_contributors_box" rel="facebox"><strong>1</strong>  contributor</a></p>
      
    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2 class="facebox-header">Users who have contributed to this file</h2>
      <ul class="facebox-user-list">
          <li class="facebox-user-list-item">
            <img alt="Thiago de Arruda" class=" js-avatar" data-user="842846" height="24" src="https://avatars3.githubusercontent.com/u/842846?s=140" width="24" />
            <a href="/tarruda">tarruda</a>
          </li>
      </ul>
    </div>
  </div>

<div class="file-box">
  <div class="file">
    <div class="meta clearfix">
      <div class="info file-name">
        <span class="icon"><b class="octicon octicon-file-text"></b></span>
        <span class="mode" title="File Mode">file</span>
        <span class="meta-divider"></span>
          <span>15 lines (14 sloc)</span>
          <span class="meta-divider"></span>
        <span>0.764 kb</span>
      </div>
      <div class="actions">
        <div class="button-group">
            <a class="minibutton tooltipped tooltipped-w"
               href="http://windows.github.com" aria-label="Open this file in GitHub for Windows">
                <span class="octicon octicon-device-desktop"></span> Open
            </a>
                <a class="minibutton tooltipped tooltipped-n js-update-url-with-hash"
                   aria-label="Clicking this button will automatically fork this project so you can edit the file"
                   href="/tarruda/bootstrap-datetimepicker/edit/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
                   data-method="post" rel="nofollow">Edit</a>
          <a href="/tarruda/bootstrap-datetimepicker/raw/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js" class="button minibutton " id="raw-url">Raw</a>
            <a href="/tarruda/bootstrap-datetimepicker/blame/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js" class="button minibutton js-update-url-with-hash">Blame</a>
          <a href="/tarruda/bootstrap-datetimepicker/commits/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js" class="button minibutton " rel="nofollow">History</a>
        </div><!-- /.button-group -->

            <a class="minibutton danger empty-icon tooltipped tooltipped-s"
               href="/tarruda/bootstrap-datetimepicker/delete/master/src/js/locales/bootstrap-datetimepicker.zh-CN.js"
               aria-label="Fork this project and delete file"
               data-method="post" data-test-id="delete-blob-file" rel="nofollow">

          Delete
        </a>
      </div><!-- /.actions -->
    </div>
        <div class="blob-wrapper data type-javascript js-blob-data">
        <table class="file-code file-diff tab-size-8">
          <tr class="file-code-line">
            <td class="blob-line-nums">
              <span id="L1" rel="#L1">1</span>
<span id="L2" rel="#L2">2</span>
<span id="L3" rel="#L3">3</span>
<span id="L4" rel="#L4">4</span>
<span id="L5" rel="#L5">5</span>
<span id="L6" rel="#L6">6</span>
<span id="L7" rel="#L7">7</span>
<span id="L8" rel="#L8">8</span>
<span id="L9" rel="#L9">9</span>
<span id="L10" rel="#L10">10</span>
<span id="L11" rel="#L11">11</span>
<span id="L12" rel="#L12">12</span>
<span id="L13" rel="#L13">13</span>
<span id="L14" rel="#L14">14</span>

            </td>
            <td class="blob-line-code"><div class="code-body highlight"><pre><div class='line' id='LC1'><span class="cm">/**</span></div><div class='line' id='LC2'><span class="cm"> * Simplified Chinese translation for bootstrap-datetimepicker</span></div><div class='line' id='LC3'><span class="cm"> * Yuan Cheung &lt;advanimal@gmail.com&gt;</span></div><div class='line' id='LC4'><span class="cm"> */</span></div><div class='line' id='LC5'><span class="p">;(</span><span class="kd">function</span><span class="p">(</span><span class="nx">$</span><span class="p">){</span></div><div class='line' id='LC6'>	<span class="nx">$</span><span class="p">.</span><span class="nx">fn</span><span class="p">.</span><span class="nx">datetimepicker</span><span class="p">.</span><span class="nx">dates</span><span class="p">[</span><span class="s1">&#39;zh-CN&#39;</span><span class="p">]</span> <span class="o">=</span> <span class="p">{</span></div><div class='line' id='LC7'>				<span class="nx">days</span><span class="o">:</span> <span class="p">[</span><span class="s2">&quot;星期日&quot;</span><span class="p">,</span> <span class="s2">&quot;星期一&quot;</span><span class="p">,</span> <span class="s2">&quot;星期二&quot;</span><span class="p">,</span> <span class="s2">&quot;星期三&quot;</span><span class="p">,</span> <span class="s2">&quot;星期四&quot;</span><span class="p">,</span> <span class="s2">&quot;星期五&quot;</span><span class="p">,</span> <span class="s2">&quot;星期六&quot;</span><span class="p">,</span> <span class="s2">&quot;星期日&quot;</span><span class="p">],</span></div><div class='line' id='LC8'>			<span class="nx">daysShort</span><span class="o">:</span> <span class="p">[</span><span class="s2">&quot;周日&quot;</span><span class="p">,</span> <span class="s2">&quot;周一&quot;</span><span class="p">,</span> <span class="s2">&quot;周二&quot;</span><span class="p">,</span> <span class="s2">&quot;周三&quot;</span><span class="p">,</span> <span class="s2">&quot;周四&quot;</span><span class="p">,</span> <span class="s2">&quot;周五&quot;</span><span class="p">,</span> <span class="s2">&quot;周六&quot;</span><span class="p">,</span> <span class="s2">&quot;周日&quot;</span><span class="p">],</span></div><div class='line' id='LC9'>			<span class="nx">daysMin</span><span class="o">:</span>  <span class="p">[</span><span class="s2">&quot;日&quot;</span><span class="p">,</span> <span class="s2">&quot;一&quot;</span><span class="p">,</span> <span class="s2">&quot;二&quot;</span><span class="p">,</span> <span class="s2">&quot;三&quot;</span><span class="p">,</span> <span class="s2">&quot;四&quot;</span><span class="p">,</span> <span class="s2">&quot;五&quot;</span><span class="p">,</span> <span class="s2">&quot;六&quot;</span><span class="p">,</span> <span class="s2">&quot;日&quot;</span><span class="p">],</span></div><div class='line' id='LC10'>			<span class="nx">months</span><span class="o">:</span> <span class="p">[</span><span class="s2">&quot;一月&quot;</span><span class="p">,</span> <span class="s2">&quot;二月&quot;</span><span class="p">,</span> <span class="s2">&quot;三月&quot;</span><span class="p">,</span> <span class="s2">&quot;四月&quot;</span><span class="p">,</span> <span class="s2">&quot;五月&quot;</span><span class="p">,</span> <span class="s2">&quot;六月&quot;</span><span class="p">,</span> <span class="s2">&quot;七月&quot;</span><span class="p">,</span> <span class="s2">&quot;八月&quot;</span><span class="p">,</span> <span class="s2">&quot;九月&quot;</span><span class="p">,</span> <span class="s2">&quot;十月&quot;</span><span class="p">,</span> <span class="s2">&quot;十一月&quot;</span><span class="p">,</span> <span class="s2">&quot;十二月&quot;</span><span class="p">],</span></div><div class='line' id='LC11'>			<span class="nx">monthsShort</span><span class="o">:</span> <span class="p">[</span><span class="s2">&quot;一月&quot;</span><span class="p">,</span> <span class="s2">&quot;二月&quot;</span><span class="p">,</span> <span class="s2">&quot;三月&quot;</span><span class="p">,</span> <span class="s2">&quot;四月&quot;</span><span class="p">,</span> <span class="s2">&quot;五月&quot;</span><span class="p">,</span> <span class="s2">&quot;六月&quot;</span><span class="p">,</span> <span class="s2">&quot;七月&quot;</span><span class="p">,</span> <span class="s2">&quot;八月&quot;</span><span class="p">,</span> <span class="s2">&quot;九月&quot;</span><span class="p">,</span> <span class="s2">&quot;十月&quot;</span><span class="p">,</span> <span class="s2">&quot;十一月&quot;</span><span class="p">,</span> <span class="s2">&quot;十二月&quot;</span><span class="p">],</span></div><div class='line' id='LC12'>			<span class="nx">today</span><span class="o">:</span> <span class="s2">&quot;今日&quot;</span></div><div class='line' id='LC13'>	<span class="p">};</span></div><div class='line' id='LC14'><span class="p">}(</span><span class="nx">jQuery</span><span class="p">));</span></div></pre></div></td>
          </tr>
        </table>
  </div>

  </div>
</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" class="js-jump-to-line" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" class="js-jump-to-line-form">
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="button">Go</button>
  </form>
</div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer">
    <ul class="site-footer-links right">
      <li><a href="https://status.github.com/">Status</a></li>
      <li><a href="http://developer.github.com">API</a></li>
      <li><a href="http://training.github.com">Training</a></li>
      <li><a href="http://shop.github.com">Shop</a></li>
      <li><a href="/blog">Blog</a></li>
      <li><a href="/about">About</a></li>

    </ul>

    <a href="/">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
    </a>

    <ul class="site-footer-links">
      <li>&copy; 2014 <span title="0.05829s from github-fe129-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="/site/terms">Terms</a></li>
        <li><a href="/site/privacy">Privacy</a></li>
        <li><a href="/security">Security</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
  </div><!-- /.site-footer -->
</div><!-- /.container -->


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-fullscreen-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="fullscreen-contents js-fullscreen-contents" placeholder="" data-suggester="fullscreen_suggester"></textarea>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-remove-close close js-ajax-error-dismiss"></a>
      Something went wrong with that request. Please try again.
    </div>


      <script crossorigin="anonymous" src="https://github.global.ssl.fastly.net/assets/frameworks-0761ba432b838d086e553e65a1be483eca0cba97.js" type="text/javascript"></script>
      <script async="async" crossorigin="anonymous" src="https://github.global.ssl.fastly.net/assets/github-320bd0f5b22fb60db7de2f691e6a8956971f5da2.js" type="text/javascript"></script>
      
      
  </body>
</html>

