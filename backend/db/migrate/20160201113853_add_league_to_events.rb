class AddLeagueToEvents < ActiveRecord::Migration
  def change
  	add_column :events, :league_id, :integer
  	add_index  :events, :league_id
  end
end
