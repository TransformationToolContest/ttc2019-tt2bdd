package org.rosi_project.example

object ModelJoinExample extends App {
    
  /*println("%%%%%%%%%%%%%%%%%%%%%% Create new views %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var eclipseView = EclipselibraryView.getNewView()
  var imdbView = ImdbdatabaseView.getNewView()
  
  println("%%%%%%%%%%%%%%%%%%%%%% Add joinable filmes %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var avaFilmRole = imdbView.createFilm(2013, "Avatar Aufbruch nach Pandory")
  var avaVideoRole = eclipseView.createVideoCassette(false, 230, "Avatar Aufbruch nach Pandory", new Date())
  var dwFilmRole = imdbView.createFilm(2018, "Death Wish")
  var asterViedeoRole = eclipseView.createVideoCassette(false, 90, "Asterix erobert Rom", new Date())
  
  println(avaVideoRole.getTitleView())
  
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  RsumCompartment.printStatus
  
  println("%%%%%%%%%%%%%%%%%%%%%% Add the join view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var joinView = JoinEclipseImdbView.getNewView()
    
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus
  
  println("%%%%%%%%%%%%%%%%%%%%%% Test Println Options for variables %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  joinView.getElementsWithClassName("JoinMovieRole").foreach(r => {
    val movie = r.asInstanceOf[JoinEclipseImdbView#JoinMovieRole]
    println(movie.getTitleView())
    println(movie.getDamagedView())
    println(movie.getMinutesLengthView())
  })
  
  println("%%%%%%%%%%%%%%%%%%%%%% Remove Avatar in the Join View %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  joinView.getElementsWithClassName("JoinMovieRole").foreach(r => {
    val movie = r.asInstanceOf[JoinEclipseImdbView#JoinMovieRole]
    if (movie.getTitleView() == "Avatar Aufbruch nach Pandory") {
      movie.deleteElement()
    }
  })
  
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus
  
  println("%%%%%%%%%%%%%%%%%%%%%% Add Death Wish in video view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var dwVideoRole = eclipseView.createVideoCassette(true, 98, "Death Wish", new Date())
  
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus  
    
  println("%%%%%%%%%%%%%%%%%%%%%% Create elements in the view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

  var movieRole = joinView.createMovie(1979, "Star Trek I", false, 132, new Date())
  //var movie2Role = joinView.createMovie(1996, "Star Trek VIII", false, 111, new Date())
  var filmRole = imdbView.createFilm(1994, "Star Trek VII")
  
  println("Year: " + movieRole.getYearView())
  println("Minutes: " + movieRole.getMinutesLengthView())
  println("Title: " + movieRole.getTitleView())
  
  var list = joinView.getAllViewElements
        
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus

  println("%%%%%%%%%%%%%%%%%%%%%% Add also video cassette from Star Trek VII %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

  var videoRole = eclipseView.createVideoCassette(false, 130, "Star Trek VII", new Date())
  
  eclipseView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus
  
  println("%%%%%%%%%%%%%%%%%%%%%% Add Relations in Film view and show results in other views %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var imdbRole = imdbView.createIMDB()
  imdbRole.addFilms(filmRole)
  
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus
  
  println("%%%%%%%%%%%%%%%%%%%%%% Add Relations in join view and show results in other views %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  
  var voteRole = joinView.createVote(6)
  movieRole.addVotes(voteRole)
  
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus

  println("%%%%%%%%%%%%%%%%%%%%%% Change the name of Star Trek I in a view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

  movieRole.setTitleView("Star Trek I The Beginning")

  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus

  println("%%%%%%%%%%%%%%%%%%%%%% Remove the movie Start Trek I %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

  movieRole.deleteElement()
  
  eclipseView.printViewRoles()
  imdbView.printViewRoles()
  joinView.printViewRoles()
  RsumCompartment.printStatus*/
}